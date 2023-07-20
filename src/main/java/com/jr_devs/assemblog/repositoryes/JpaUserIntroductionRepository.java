package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.models.user.UserIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaUserIntroductionRepository extends JpaRepository<UserIntroduction, Long> {

    UserIntroduction save(UserIntroduction userIntroduction);

    UserIntroduction findByUserId(Long userId);

    @Query(value = "CALL get_user_introduction_list(:user_id)", nativeQuery = true)
    List<UserIntroduction> findUserIntroductionList(@Param("user_id") Long userId);
}
