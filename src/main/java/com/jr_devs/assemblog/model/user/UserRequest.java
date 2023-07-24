package com.jr_devs.assemblog.model.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String username;
    private String email;
    private String password;
    private String profileImageURL;
}
