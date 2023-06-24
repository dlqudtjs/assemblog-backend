package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.User;
import com.jr_devs.assemblog.repositoryes.JpaUserRepository;
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
    private JpaUserRepository userRepository;

    @Autowired
    private UserService userService;

    public UserServiceTest() {
    }

    @Test
    @DisplayName("회원가입 테스트")
    void join() {
        User user = new User();

        user.setUsername("user1");
        user.setEmail("user1@gmail.com");
        user.setPassword("1234");

        User joinedUser = this.userService.join(user);
        assertThat(joinedUser).isSameAs(this.userRepository.findById(user.getId()).get());
    }

    @Test
    @DisplayName("유저네임 중복 테스트")
    void DuplicateUsernameCheck() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("1234");

        User user2 = new User();
        user2.setUsername("user1");
        user2.setEmail("user2@gmail.com");
        user2.setPassword("1234");

        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            userService.join(user2);
        });

        assertThat(e.getMessage()).isEqualTo("Username Already exist");
    }

    @Test
    @DisplayName("이메일 중복 테스트")
    void DuplicateEmailCheck() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("1234");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user1@gmail.com");
        user2.setPassword("1234");

        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            userService.join(user2);
        });

        assertThat(e.getMessage()).isEqualTo("Email Already exist");
    }
}
