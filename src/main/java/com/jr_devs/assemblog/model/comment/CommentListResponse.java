package com.jr_devs.assemblog.model.comment;

import jakarta.persistence.Column;
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

    @Column(name = "writer_id")
    private Long writerId;

    private String nickname;

    private String content;

    private Long parentCommentId;

    private boolean likeState;

    private String createdAt;

    private boolean isWriter;

    private boolean deleted;
}
