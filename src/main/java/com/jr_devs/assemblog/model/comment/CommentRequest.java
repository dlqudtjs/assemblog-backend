package com.jr_devs.assemblog.model.comment;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private Long postId;

    private String nickname;

    private String content;

    private Long parentCommentId;

    private String password;
}
