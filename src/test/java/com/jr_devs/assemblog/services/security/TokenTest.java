package com.jr_devs.assemblog.services.security;

import com.jr_devs.assemblog.models.UserDto;
import com.jr_devs.assemblog.services.user.UserService;
import com.jr_devs.assemblog.token.JwtProvider;
import com.jr_devs.assemblog.token.TokenDto;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TokenTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("createAllToken 및 matchingTokenAndEmail 테스트")
    public void matchingTokenAndEmail() {
        // given
        UserDto userDto = new UserDto();
        userDto.setUsername("user1");
        userDto.setEmail("user1@gmail.com");
        userDto.setPassword("1234");

        // when
        TokenDto tokenDto = jwtProvider.createAllToken(userDto.getEmail());

        // then
        assertThat(jwtProvider.matchingTokenAndEmail(tokenDto.getAccessToken(), userDto.getEmail())).isTrue();
        assertThat(jwtProvider.matchingTokenAndEmail(tokenDto.getRefreshToken(), userDto.getEmail())).isTrue();
    }

    @Test
    @DisplayName("jwtProvider.loginLogic 실행시 TokenDto 반환 및 검증 테스트")
    public void createAndValidateTokenTest() {
        // given
        UserDto userDto = new UserDto();
        userDto.setUsername("user1");
        userDto.setEmail("user1@gmail.com");
        userDto.setPassword("1234");

        // when
        TokenDto tokenDto = jwtProvider.loginLogic(userDto.getEmail());

        // then
        assertThat(jwtProvider.validateAccessToken(tokenDto.getAccessToken(), userDto.getEmail())).isTrue();
        assertThat(jwtProvider.validateRefreshToken(tokenDto.getRefreshToken(), userDto.getEmail())).isTrue();
    }
}