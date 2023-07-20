package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.repositoryes.JpaRefreshTokenRepository;
import com.jr_devs.assemblog.token.JwtProvider;
import com.jr_devs.assemblog.token.RefreshToken;
import com.jr_devs.assemblog.token.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;
    private final JpaRefreshTokenRepository refreshTokenRepository;

    public TokenDto refresh(String token) {
        // 토큰 검사
        if (jwtProvider.validateToken(token) && jwtProvider.refreshTokenDatabaseMatching(token)) {

            // 토큰에서 이메일을 가져온다.
            String email = jwtProvider.getEmailFromToken(token);

            // Access Token, Refresh Token 생성
            TokenDto tokenDto = jwtProvider.createAllToken(email);

            // DB 에 Refresh Token 이 존재하는지 검사
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(email);

            // DB 에 Refresh Token 이 존재하면 Refresh Token 을 삭제한다.
            if (refreshToken.isPresent()) {
                refreshTokenRepository.deleteByEmail(email);
            }

            refreshTokenRepository.flush();

            // DB 에 Refresh Token 을 저장한다.
            refreshTokenRepository.save(RefreshToken.builder()
                    .refreshToken(tokenDto.getRefresh_token())
                    .email(email)
                    .build());

            // 토큰 반환
            return tokenDto;
        }

        return null;
    }
}
