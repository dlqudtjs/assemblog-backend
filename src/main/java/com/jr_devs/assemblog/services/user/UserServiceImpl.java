package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.models.User;
import com.jr_devs.assemblog.models.dtos.UserDto;
import com.jr_devs.assemblog.repositoryes.JpaRefreshTokenRepository;
import com.jr_devs.assemblog.repositoryes.JpaUserRepository;
import com.jr_devs.assemblog.token.JwtProvider;
import com.jr_devs.assemblog.token.TokenDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final HttpServletResponse response;

    @Override
    public ResponseDto login(UserDto userDto) {
        // 이메일 검사
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Not found Email"));

        // 비밀번호 검사
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Not Match Password");
        }

        TokenDto tokenDto = jwtProvider.loginLogic(user.getEmail());

        // Response Header 에 Access Token, Refresh Token 을 추가한다.
        setHeader(response, tokenDto);

        return new ResponseDto(user.getEmail(), HttpStatus.OK.value());
    }

    @Override
    public ResponseDto join(UserDto userDto) {
        this.checkDuplicate(userDto);

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();

        // 회원가입
        userRepository.save(user);

        // 회원가입 성공시 응답
        return ResponseDto.builder()
                .message("Success signup")
                .statusCode(HttpStatus.OK.value()) // int 형이기 때문에 value()를 사용해야 한다.
                .build();
    }

    @Override
    public String getUsernameByEmail(String email) {
        return userRepository.findByEmail(email).get().getUsername();
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.setHeader("accesstoken", tokenDto.getAccessToken());
        response.setHeader("refreshtoken", tokenDto.getRefreshToken());
    }

    private void checkDuplicate(UserDto userDto) {
        this.userRepository.findByUsername(userDto.getUsername()).ifPresent((findUser) -> {
            throw new IllegalStateException("Username Already exist");
        });
        this.userRepository.findByEmail(userDto.getEmail()).ifPresent((findUser) -> {
            throw new IllegalStateException("Email Already exist");
        });
    }
}
