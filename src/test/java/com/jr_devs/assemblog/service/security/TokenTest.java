package com.jr_devs.assemblog.service.security;

import com.jr_devs.assemblog.model.user.UserResponse;
import com.jr_devs.assemblog.service.user.UserService;
import com.jr_devs.assemblog.token.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
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

    private UserResponse userDto;

}