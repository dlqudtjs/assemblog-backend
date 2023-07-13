package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.token.JwtProvider;
import com.jr_devs.assemblog.token.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;

    public TokenDto refresh(String token) {
        // 토큰 검사
        if (jwtProvider.validateRefreshToken(token)) {
            // 토큰에서 이메일을 가져온다.
            String email = jwtProvider.getEmailFromToken(token);

            // 토큰 재발급
            TokenDto tokenDto = jwtProvider.loginLogic(email);

            // 토큰 반환
            return tokenDto;
        }

        return null;
    }
}
