package com.jr_devs.assemblog.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_introduction")
public class UserIntroduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String username;

    private String introduction;

    @Column(name = "profile_image_url")
    private String profileImageURL;

    @Column(name = "background_image_url")
    private String backgroundImageURL;

}
