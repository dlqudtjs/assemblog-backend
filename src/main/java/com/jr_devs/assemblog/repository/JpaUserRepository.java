package com.jr_devs.assemblog.repository;

import com.jr_devs.assemblog.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    User save(User member);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findAll();
}
