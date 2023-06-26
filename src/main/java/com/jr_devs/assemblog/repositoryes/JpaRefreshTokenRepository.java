package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByEmail(String email);

    void deleteById(Long id);
}
