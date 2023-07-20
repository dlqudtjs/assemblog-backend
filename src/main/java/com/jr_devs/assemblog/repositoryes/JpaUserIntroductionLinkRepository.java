package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.models.user.UserIntroductionLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JpaUserIntroductionLinkRepository extends JpaRepository<UserIntroductionLink, Long> {

    UserIntroductionLink save(UserIntroductionLink userIntroductionLink);

    List<UserIntroductionLink> findByUserId(Long userId);
}
