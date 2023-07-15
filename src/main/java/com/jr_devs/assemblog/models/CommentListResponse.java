package com.jr_devs.assemblog.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentListResponse {

    private Long id;

    private String nickname;

    private String content;

    private int parentCommentId;

    private int depth;

    private String createdAt;

    private boolean likeState;

    private boolean deleted;
}
