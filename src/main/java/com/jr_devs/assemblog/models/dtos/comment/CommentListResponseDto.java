package com.jr_devs.assemblog.models.dtos.comment;

import com.jr_devs.assemblog.models.CommentListResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponseDto {

    List<CommentListResponse> commentList;

    // for error
    int statusCode;
    String message;
}
