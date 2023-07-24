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
@Table(name = "user_introduction_link")
public class UserIntroductionLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String linkDescription;

    @Column(name = "link_url")
    private String linkURL;

    @Column(name = "link_image_url")
    private String linkImageURL;
}
