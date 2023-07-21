package com.jr_devs.assemblog.models.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String username;
    private String email;
    private String password;
    private String profileImageURL;
}
