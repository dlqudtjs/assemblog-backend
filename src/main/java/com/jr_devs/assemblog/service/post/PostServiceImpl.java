package com.jr_devs.assemblog.service.post;

import com.jr_devs.assemblog.model.dto.*;
import com.jr_devs.assemblog.model.post.*;
import com.jr_devs.assemblog.model.tag.Tag;
import com.jr_devs.assemblog.model.tag.TagDto;
import com.jr_devs.assemblog.repository.JpaBoardRepository;
import com.jr_devs.assemblog.repository.JpaPostRepository;
import com.jr_devs.assemblog.repository.JpaPostViewCountRepository;
import com.jr_devs.assemblog.service.board.BoardService;
import com.jr_devs.assemblog.service.comment.CommentService;
import com.jr_devs.assemblog.service.tag.PostTagService;
import com.jr_devs.assemblog.service.tag.TagService;
import com.jr_devs.assemblog.service.user.UserService;
import com.jr_devs.assemblog.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final JpaPostRepository postRepository;
    private final JpaBoardRepository boardRepository;
    private final TagService tagService;
    private final PostTagService postTagService;
    private final BoardService boardService;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final CommentService commentService;
    private final JpaPostViewCountRepository postViewCountRepository;

    /*
     * 작성 시 같은 제목의 임시 저장 글이 있으면 삭제한다.
     * 게시글을 생성하고, 태그를 생성하고, 게시글과 태그를 연결한다.
     */
    @Override
    public ResponseDto createPost(PostDto postDto) {
        Post post = buildPost(postDto);

        List<Post> tempSavePost = getTempSavePosts(postDto.getWriterMail());

        System.out.println(tempSavePost.size());

        // 작성 버튼을 눌렀을 때 같은 제목의 임시 저장 글이 있으면 삭제
        for (Post p : tempSavePost) {
            if (p.getTitle().equals(postDto.getTitle())) {
                postRepository.deleteByTitle(p.getTitle());
                break;
            }
        }

        // 게시글 생성
        Post savedPost = postRepository.save(post);

        for (String tagName : postDto.getTags()) {
            // 붙은 태그 생성 (중복 검사 실시)
            Tag tag = tagService.createTag(TagDto.builder()
                    .name(tagName)
                    .build());

            // 게시글과 태그 연결 (게시글 내 태그 중복 검사)
            postTagService.createPostTag(savedPost.getId(), tag.getId());
        }

        return createResponse(HttpStatus.OK.value(), "Success create post");
    }

    /*
     * 임시 저장 글이 있으면 덮어쓰기를 한다.
     * 덮어쓰기를 할 때 기존의 태그를 모두 삭제한 후 새로운 태그를 붙인다. (덮어쓰기)
     * 모두 삭제할 때, 태그가 더이상 참조하는 게시글이 없을 경우 태그 자체를 삭제한다.
     */
    @Override
    public ResponseDto tempSavePost(PostDto postDto, String token) {
        Post findPost = postRepository.findByWriterMailAndTitleAndTempSaveState(postDto.getWriterMail(), postDto.getTitle(), true);
        Post savedPost = null;

        if (findPost == null) {
            savedPost = postRepository.save(buildPost(postDto));
        } else { // 같은 제목의 임시 저장 글이 있을 때,
            // 토큰 내 작성자와 임시 저장 글의 작성자가 같은지 검사
            if (!jwtProvider.getEmailFromToken(token).equals(findPost.getWriterMail())) {
                return createResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
            }

            // 임시 저장 글 덮어쓰기
            overwritePost(findPost, postDto);

            // 기존 태그 모두 삭제
            List<PostTag> postTags = postTagService.getPostTagsByPostId(findPost.getId());
            for (PostTag postTag : postTags) {
                tagService.deleteTag(postTag.getTagId());
            }
        }

        // 찾은 게시글이 없으면 새로 생성한 게시글의 id 를, 있으면 찾은 게시글의 id 를 postId 에 저장
        Long postId = (savedPost == null) ? findPost.getId() : savedPost.getId();

        // 태그 새로 붙이기
        for (String tagName : postDto.getTags()) {
            // 붙은 태그 생성 (중복 검사 실시)
            Tag tag = tagService.createTag(TagDto.builder()
                    .name(tagName)
                    .build());

            // 게시글과 태그 연결 (게시글 내 태그 중복 검사)
            postTagService.createPostTag(postId, tag.getId());
        }

        return createResponse(HttpStatus.OK.value(), "Success temp save post");
    }

    // 게시글 조회
    @Override
    public PostResponseDto readPost(Long postId) {
        System.out.println(1);
        Post post = postRepository.findById(postId).orElse(null);

        if (post == null && post.isTempSaveState()) {
            return PostResponseDto.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message("Not found post")
                    .build();
        }

        System.out.println(2);
        // 게시글의 태그 목록 가져오기
        List<PostTag> postTags = postTagService.getPostTagsByPostId(postId);
        // 태그 Id 목록을 태그 이름 목록으로 변환
        List<String> tagList = new ArrayList<>();
        for (PostTag postTag : postTags) {
            tagList.add(tagService.readTagById(postTag.getTagId()).getName());
        }

        System.out.println(3);
        return PostResponseDto.builder()
                .postId(post.getId())
                .username(userService.getUsernameByEmail(post.getWriterMail()))
                .writerMail(post.getWriterMail())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .postUseState(post.isPostUseState())
                .commentUseState(post.isCommentUseState())
                .tempSaveState(post.isTempSaveState())
                .categoryTitle(boardService.getCategoryTitleByBoardId(post.getBoardId()))
                .boardTitle(boardService.getBoardTitle(post.getBoardId()))
                .viewCount(getPostViewCount(post.getId()))
                .tagList(tagList)
                .statusCode(HttpStatus.OK.value())
                .message("Success read post")
                .build();
    }

    /*
     * 덮어쓰기를 할 때 기존의 태그를 모두 삭제한 후 새로운 태그를 붙인다. (덮어쓰기)
     * 업데이트 시 기존의 태그를 모두 삭제한 후 새로운 태그를 붙인다. (덮어쓰기)
     */
    @Override
    public ResponseDto updatePost(PostDto postDto, String token) {
        Post findPost = postRepository.findById(postDto.getId()).orElse(null);

        if (findPost == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist post");
        }

        // 토큰 내 작성자와 게시글의 작성자가 같은지 검사
        if (!jwtProvider.getEmailFromToken(token).equals(findPost.getWriterMail())) {
            return createResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        }

        // 게시글 덮어쓰기
        overwritePost(findPost, postDto);

        // 게시글과 태그 연결 삭제
        deleteTags(postDto.getId());

        // 태그 새로 붙이기
        for (String tagName : postDto.getTags()) {
            // 붙은 태그 생성 (이미 생성된 태그 중복 검사 실시)
            Tag tag = tagService.createTag(TagDto.builder()
                    .name(tagName)
                    .build());

            // 게시글과 태그 연결 (게시글 내 태그 중복 검사)
            postTagService.createPostTag(postDto.getId(), tag.getId());
        }

        return createResponse(HttpStatus.OK.value(), "Success update post");
    }

    /*
     * 게시글 삭제 시 태그가 더이상 참조 하는 게시글이 없으면 태그 자체를 삭제한다.
     */
    @Override
    public ResponseDto deletePost(Long postId, String token) {
        Post findPost = postRepository.findById(postId).orElse(null);

        if (findPost == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist post");
        }

        // 토큰 내 작성자와 삭제할 게시글의 작성자가 같은지 검사
        if (!jwtProvider.getEmailFromToken(token).equals(findPost.getWriterMail())) {
            return createResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        }

        // 게시글과 태그 연결 삭제
        deleteTags(postId);

        postRepository.deleteById(postId);

        return createResponse(HttpStatus.OK.value(), "Success delete post");
    }

    // 게시글 목록 조회 (옵션을 이용하여 정렬 및 검색 가능)
    @Override
    public PostListResponseDto readPostList(int currentPage, int pageSize, String order, String orderType, String boardTitle, String tagName) {
        currentPage = (currentPage <= 0) ? 1 : currentPage;
        int pageStartIndex = (currentPage - 1) * pageSize;

        List<Post> postList;
        long boardId = boardTitle.equals("all") ? 0 : boardRepository.findByTitle(boardTitle).get().getId();
        long tagId = tagName.equals("all") ? 0 : tagService.readTagByName(tagName).getId();

        postList = postRepository.findPostList(pageStartIndex, pageSize, order, orderType, boardId, tagId);

        List<PostListResponse> postListResponse = new ArrayList<>();
        for (Post post : postList) {
            postListResponse.add(PostListResponse.builder()
                    .postId(post.getId())
                    .username(userService.getUsernameByEmail(post.getWriterMail()))
                    .title(post.getTitle())
                    .thumbnail(post.getThumbnail())
                    .preview(post.getPreview())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .categoryTitle(boardService.getCategoryTitleByBoardId(post.getBoardId()))
                    .boardTitle(boardService.getBoardTitle(post.getBoardId()))
                    .viewCount(getPostViewCount(post.getId()))
                    .likeCount(0)
                    .commentCount(commentService.getCommentCount(post.getId()))
                    .build());
        }

        int postCount = postRepository.findPostCount(boardId, tagId);
        int totalPage = (postCount % pageSize == 0) ? postCount / pageSize : postCount / pageSize + 1;

        return PostListResponseDto.builder()
                .totalPage(totalPage)
                .currentPage(currentPage)
                .postList(postListResponse)
                .statusCode(HttpStatus.OK.value())
                .message("Success read post list")
                .build();
    }

    @Override
    public void countView(Long postId) {
        postViewCountRepository.save(PostViewCount.builder()
                .postId(postId)
                .build());
    }

    private int getPostViewCount(Long postId) {
        return postViewCountRepository.countByPostId(postId);
    }

    // 태그를 삭제하며 태그가 더이상 참조하는 게시글이 없으면 태그 자체를 삭제한다.
    private void deleteTags(Long postId) {
        List<PostTag> postTags = postTagService.getPostTagsByPostId(postId);

        // 1. 게시글과 태그 연결 삭제
        for (PostTag postTag : postTags) {
            postTagService.deletePostTag(postTag.getPostId(), postTag.getTagId());
        }

        // 2. 태그가 더이상 참조하는 게시글이 없으면 태그 자체를 삭제
        for (PostTag postTag : postTags) {
            if (postTagService.getPostTagsByTagId(postTag.getTagId()).size() == 0) {
                tagService.deleteTag(postTag.getTagId());
            }
        }
    }

    // 게시글 덮어쓰기
    private void overwritePost(Post findPost, PostDto postDto) {
        findPost.setBoardId(postDto.getBoardId());
        findPost.setTitle(postDto.getTitle());
        findPost.setContent(postDto.getContent());
        findPost.setThumbnail(postDto.getThumbnail());
        findPost.setPostUseState(postDto.isPostUseState());
        findPost.setCommentUseState(postDto.isCommentUseState());
        findPost.setTempSaveState(postDto.isTempSaveState());
        findPost.setPreview(postDto.getPreview());
    }

    // 사용자가 임시저장한 게시글 목록 조회
    private List<Post> getTempSavePosts(String writerMail) {
        return postRepository.findByWriterMailAndTempSaveState(writerMail, true);
    }

    private ResponseDto createResponse(int statusCode, String message) {
        return ResponseDto.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    private Post buildPost(PostDto postDto) {
        return Post.builder()
                .boardId(postDto.getBoardId())
                .writerMail(postDto.getWriterMail())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .thumbnail(postDto.getThumbnail())
                .postUseState(postDto.isPostUseState())
                .commentUseState(postDto.isCommentUseState())
                .tempSaveState(postDto.isTempSaveState())
                .preview(postDto.getPreview())
                .build();
    }
}
