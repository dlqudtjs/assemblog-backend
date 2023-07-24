package com.jr_devs.assemblog.repository;

import com.jr_devs.assemblog.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByEmail(String email);

    void deleteByEmail(String email);

    void flush();
}
