package com.jr_devs.assemblog.service.comment;

import com.jr_devs.assemblog.model.comment.Comment;
import com.jr_devs.assemblog.model.comment.CommentListResponse;
import com.jr_devs.assemblog.model.comment.CommentRequest;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.post.Post;
import com.jr_devs.assemblog.model.user.User;
import com.jr_devs.assemblog.repository.JpaCommentRepository;
import com.jr_devs.assemblog.repository.JpaPostRepository;
import com.jr_devs.assemblog.repository.JpaUserRepository;
import com.jr_devs.assemblog.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final JpaCommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JpaPostRepository postRepository;
    private final JpaUserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDto createComment(CommentRequest commentRequest, String token) {
        Post post = postRepository.findById(commentRequest.getPostId()).orElse(null);

        if (post == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Post not found");
        }

        // 부모 댓글이 0이 아닌데, 부모 댓글이 없을 경우
        if (commentRequest.getParentCommentId() != 0 && commentRepository.findById(commentRequest.getParentCommentId()).isEmpty()) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Parent Comment Not found");
        }

        Long writerId;
        boolean isWriter = false;

        if (jwtProvider.validateToken(token)) {
            User user = userRepository.findByEmail(jwtProvider.getEmailFromToken(token)).get();

            writerId = user.getId();
            commentRequest.setPassword(null);
            isWriter = post.getWriterMail().equals(jwtProvider.getEmailFromToken(token));
        } else {
            commentRequest.setPassword(passwordEncoder.encode(commentRequest.getPassword()));
            writerId = 0L;
        }

        commentRepository.save(Comment.builder()
                .postId(commentRequest.getPostId())
                .writerId(writerId)
                .nickname(commentRequest.getNickname())
                .content(commentRequest.getContent())
                .password(commentRequest.getPassword())
                .parentCommentId(commentRequest.getParentCommentId())
                .likeState(false)
                .isWriter(isWriter)
                .deleted(false)
                .build());

        return createResponse(HttpStatus.OK.value(), "Success write comment");
    }

    @Transactional
    @Override
    public ResponseDto deleteComment(Long commentId, String password) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        Comment parentComment = commentRepository.findById(comment.getParentCommentId()).orElse(null);

        if (comment == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Comment not found");
        }

        // password 로 검증된 token 이 들어온다면 비밀번호를 검사하지 않고 삭제한다. (관리자)
        if (password.startsWith("Bearer ") && !jwtProvider.validateToken(password.substring(7))) {
            if (!passwordEncoder.matches(password, comment.getPassword())) {
                return createResponse(HttpStatus.BAD_REQUEST.value(), "Password is incorrect");
            }
        }

        // 자신이 부모 댓글이라면
        if (comment.getParentCommentId() == 0) {
            // 자신 댓글에 대댓글이 있다면
            if (commentRepository.countAllByParentCommentId(commentId) > 0) {
                comment.setNickname("삭제된 사용자");
                comment.setContent("삭제된 댓글입니다.");
                comment.setDeleted(true);
            } else {
                commentRepository.deleteById(commentId);
            }
        }
        // 자신이 대댓글 이라면 바로 삭제
        else {
            commentRepository.deleteById(commentId);

            // db 적용
            commentRepository.flush();

            // 자신의 부모 댓글이 삭제 돼었고, 대댓글이 없다면 부모 댓글도 삭제
            if (parentComment.isDeleted() && commentRepository.countAllByParentCommentId(comment.getParentCommentId()) == 0) {
                commentRepository.deleteById(comment.getParentCommentId());
            }
        }

        return createResponse(HttpStatus.OK.value(), "Success delete comment");
    }

    @Override
    public List<CommentListResponse> readCommentList(Long postId) {
        List<Comment> commentList = commentRepository.findAllByPostId(postId);

        ArrayList<CommentListResponse> commentListResponse = new ArrayList<>();
        for (Comment comment : commentList) {
            commentListResponse.add(CommentListResponse.builder()
                    .id(comment.getId())
                    .writerId(comment.getWriterId())
                    .nickname(comment.getNickname())
                    .content(comment.getContent())
                    .parentCommentId(comment.getParentCommentId())
                    .createdAt(comment.getCreatedAt())
                    .likeState(comment.isLikeState())
                    .deleted(comment.isDeleted())
                    .build());
        }

        return commentListResponse;
    }

    @Transactional
    @Override
    public ResponseDto likeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Comment not found");
        }

        comment.setLikeState(!comment.isLikeState());

        return createResponse(HttpStatus.OK.value(), "Success like comment");
    }

    @Override
    public int getCommentCount(Long postId) {
        return commentRepository.countAllByPostId(postId);
    }

    private ResponseDto createResponse(int statusCode, String message) {
        return ResponseDto.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
