package com.jr_devs.assemblog.service.comment;

import com.jr_devs.assemblog.model.comment.CommentRequest;
import com.jr_devs.assemblog.model.comment.CommentListResponse;
import com.jr_devs.assemblog.model.dto.ResponseDto;

import java.util.List;

public interface CommentService {

    ResponseDto createComment(CommentRequest commentDto, String token);

    List<CommentListResponse> readCommentList(Long postId);

    ResponseDto deleteComment(Long commentId, String password);

    int getCommentCount(Long postId);

    ResponseDto likeComment(Long commentId);
}
