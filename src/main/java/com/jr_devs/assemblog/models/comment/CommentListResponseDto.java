package com.jr_devs.assemblog.models.comment;

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
