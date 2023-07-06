package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.models.dtos.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입 테스트")
    void join() {
        // given
        UserDto userDto = new UserDto();
        userDto.setUsername("user1");
        userDto.setEmail("user1@gmail.com");
        userDto.setPassword("1234");

        // when
        ResponseDto responseDto = this.userService.join(userDto);

        // then
        // 가입된 유저와 userForm 으로 입력한 유저가 같은지 확인
        System.out.println(responseDto.getMessage() + " - " + responseDto.getStatusCode());
    }

    @Test
    @DisplayName("유저네임 중복 테스트")
    void DuplicateUsernameCheck() {
        // given
        UserDto userDto1 = new UserDto();
        userDto1.setUsername("user1");
        userDto1.setEmail("user1@gmail.com");
        userDto1.setPassword("1234");

        UserDto userDto2 = new UserDto();
        userDto2.setUsername("user1");
        userDto2.setEmail("user2@gmail.com");
        userDto2.setPassword("1234");

        // when
        userService.join(userDto1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            userService.join(userDto2);
        });

        // then
        assertThat(e.getMessage()).isEqualTo("Username Already exist");
    }

    @Test
    @DisplayName("이메일 중복 테스트")
    void DuplicateEmailCheck() {
        // given
        UserDto userDto1 = new UserDto();
        userDto1.setUsername("user1");
        userDto1.setEmail("user1@gmail.com");
        userDto1.setPassword("1234");

        UserDto userDto2 = new UserDto();
        userDto2.setUsername("user2");
        userDto2.setEmail("user1@gmail.com");
        userDto2.setPassword("1234");

        // when
        userService.join(userDto1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            userService.join(userDto2);
        });

        // then
        assertThat(e.getMessage()).isEqualTo("Email Already exist");
    }

    // todo jwt 로그인 테스트 구현하기
    @Test
    @DisplayName("로그인 테스트")
    void login() {
        // given
        UserDto userDto = new UserDto();
        userDto.setUsername("loginTestUser");
        userDto.setEmail("loginTestUser@gmail.com");
        userDto.setPassword("1234");

        // when
        userService.join(userDto);

        // then
        ResponseDto responseDto = userService.login(userDto);
        assertThat(responseDto.getMessage()).isEqualTo("loginTestUser@gmail.com");
    }
}
