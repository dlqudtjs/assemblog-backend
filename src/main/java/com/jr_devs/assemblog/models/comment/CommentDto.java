package com.jr_devs.assemblog.models.comment;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long postId;

    private String nickname;

    private String content;

    private int parentCommentId;

    private int depth;

    private String password;

    private String createdAt;

    private boolean likeState;
}
