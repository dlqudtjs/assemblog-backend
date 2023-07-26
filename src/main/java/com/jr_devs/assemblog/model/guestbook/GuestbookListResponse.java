package com.jr_devs.assemblog.model.guestbook;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookListResponse {

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
