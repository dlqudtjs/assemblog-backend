package com.jr_devs.assemblog.services.comment;

import com.jr_devs.assemblog.models.comment.CommentDto;
import com.jr_devs.assemblog.models.dto.ResponseDto;
import com.jr_devs.assemblog.models.comment.CommentListResponseDto;

public interface CommentService {

    ResponseDto createComment(CommentDto commentDto);

    CommentListResponseDto readCommentList(Long postId);

    ResponseDto deleteComment(Long commentId, String password);

    int getCommentCount(Long postId);

    ResponseDto likeComment(Long commentId);
}
