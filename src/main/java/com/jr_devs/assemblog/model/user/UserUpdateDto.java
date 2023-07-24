package com.jr_devs.assemblog.model.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {

    private String username;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String profileImageUrl;
}
