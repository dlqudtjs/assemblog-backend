package com.jr_devs.assemblog.services.comment;

import com.jr_devs.assemblog.models.comment.Comment;
import com.jr_devs.assemblog.models.comment.CommentListResponse;
import com.jr_devs.assemblog.models.comment.CommentDto;
import com.jr_devs.assemblog.models.dto.ResponseDto;
import com.jr_devs.assemblog.models.comment.CommentListResponseDto;
import com.jr_devs.assemblog.repositoryes.JpaCommentRepository;
import com.jr_devs.assemblog.repositoryes.JpaPostRepository;
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

    @Override
    public ResponseDto createComment(CommentDto commentDto) {
        if (postRepository.findById(commentDto.getPostId()).isEmpty()) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Post not found");
        }

        commentRepository.save(Comment.builder()
                .postId(commentDto.getPostId())
                .nickname(commentDto.getNickname())
                .content(commentDto.getContent())
                .parentCommentId(commentDto.getParentCommentId())
                .depth(commentDto.getDepth())
                .password(passwordEncoder.encode(commentDto.getPassword()))
                .likeState(false)
                .build());

        return createResponse(HttpStatus.OK.value(), "Success write comment");
    }

    @Transactional
    @Override
    public ResponseDto deleteComment(Long commentId, String password) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Comment not found");
        }

        if (!passwordEncoder.matches(password, comment.getPassword())) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Password is incorrect");
        }

        comment.setDeleted(true);

        return createResponse(HttpStatus.OK.value(), "Success delete comment");
    }

    @Override
    public CommentListResponseDto readCommentList(Long postId) {
        List<Comment> commentList = commentRepository.findAllByPostId(postId);

        ArrayList<CommentListResponse> commentListResponse = new ArrayList<>();
        for (Comment comment : commentList) {
            commentListResponse.add(CommentListResponse.builder()
                    .id(comment.getId())
                    .nickname(comment.getNickname())
                    .content(comment.getContent())
                    .parentCommentId(comment.getParentCommentId())
                    .depth(comment.getDepth())
                    .createdAt(comment.getCreatedAt())
                    .likeState(comment.isLikeState())
                    .deleted(comment.isDeleted())
                    .build());
        }

        return CommentListResponseDto.builder()
                .commentList(commentListResponse)
                .statusCode(HttpStatus.OK.value())
                .message("Success read comment list")
                .build();
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
