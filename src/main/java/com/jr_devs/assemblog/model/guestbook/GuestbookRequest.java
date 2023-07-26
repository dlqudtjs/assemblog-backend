package com.jr_devs.assemblog.model.guestbook;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookRequest {

    private String nickname;

    private String content;

    private String password;

    private Long parentCommentId;
}
