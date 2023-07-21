package com.jr_devs.assemblog.models.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIntroductionResponse {

    private String username;

    private String email;

    private String introduction;

    private String profileImageURL;

    private String backgroundImageURL;

    private List<UserIntroductionLink> links;
}
