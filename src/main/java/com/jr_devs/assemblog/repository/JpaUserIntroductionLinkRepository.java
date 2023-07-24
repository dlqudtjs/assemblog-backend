package com.jr_devs.assemblog.repository;

import com.jr_devs.assemblog.model.user.UserIntroductionLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JpaUserIntroductionLinkRepository extends JpaRepository<UserIntroductionLink, Long> {

    UserIntroductionLink save(UserIntroductionLink userIntroductionLink);

    List<UserIntroductionLink> findByUserId(Long userId);
}
