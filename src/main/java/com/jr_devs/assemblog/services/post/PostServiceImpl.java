package com.jr_devs.assemblog.services.post;

import com.jr_devs.assemblog.models.*;
import com.jr_devs.assemblog.repositoryes.JpaPostRepository;
import com.jr_devs.assemblog.services.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final JpaPostRepository postRepository;

    private final TagService tagService;

    // todo post_tag도 저장해야 됨
    @Override
    public ResponseDto createPost(PostDto postDto) {
        Post post = buildPost(postDto);

        List<Post> tempSavePost = getTempSavePosts(postDto.getWriterMail(), true);

        System.out.println(tempSavePost.size());

        // 작성 버튼을 눌렀을 때 같은 제목의 임시 저장 글이 있으면 삭제
        for (Post p : tempSavePost) {
            if (p.getTitle().equals(postDto.getTitle())) {
                postRepository.deleteByTitle(p.getTitle());
                break;
            }
        }

        for (String tagName : postDto.getTags()) {
            tagService.createTag(TagDto.builder()
                    .name(tagName)
                    .build());
        }

        postRepository.save(post);

        return createResponse(HttpStatus.OK.value(), "Success create post");
    }

    // todo 임시저장에서 불러올 때 게시물에 붙은 태그도 같이 가져와야함
    @Override
    public ResponseDto tempSavePost(PostDto postDto) {
        Post findPost = postRepository.findByWriterMailAndTitleAndTempSaveState(postDto.getWriterMail(), postDto.getTitle(), true);

        if (findPost == null) {
            postRepository.save(buildPost(postDto));
        } else {
            // 임시 저장 글이 있으면 덮어쓰기
            findPost.setBoardId(postDto.getBoardId());
            findPost.setTitle(postDto.getTitle());
            findPost.setContent(postDto.getContent());
            findPost.setThumbnail(postDto.getThumbnail());
            findPost.setPostUseState(postDto.isPostUseState());
            findPost.setCommentUseState(postDto.isCommentUseState());
            findPost.setTempSaveState(postDto.isTempSaveState());
            findPost.setPreview(postDto.getPreview());
        }

        return createResponse(HttpStatus.OK.value(), "Success temp save post");
    }

    @Override
    public ResponseDto updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public ResponseDto deletePost(Long postId) {
        Post findPost = postRepository.findById(postId).orElse(null);

        if (findPost == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist post");
        }

        postRepository.deleteById(postId);

        return createResponse(HttpStatus.OK.value(), "Success delete post");
    }

    private List<Post> getTempSavePosts(String writerMail, boolean tempSaveState) {
        return postRepository.findByWriterMailAndTempSaveState(writerMail, tempSaveState);
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
