package com.jr_devs.assemblog.model.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {

    private String email;
    private String oldPassword;
    private String newPassword;
}
