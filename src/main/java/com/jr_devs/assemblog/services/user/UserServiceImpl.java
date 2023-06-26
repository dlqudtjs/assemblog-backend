package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.models.User;
import com.jr_devs.assemblog.models.UserDto;
import com.jr_devs.assemblog.repositoryes.JpaRefreshTokenRepository;
import com.jr_devs.assemblog.repositoryes.JpaUserRepository;
import com.jr_devs.assemblog.token.JwtProvider;
import com.jr_devs.assemblog.token.RefreshToken;
import com.jr_devs.assemblog.token.TokenDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JpaUserRepository userRepository;
    private final JpaRefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDto login(UserDto UserForm, HttpServletResponse response) {
        // 이메일 검사
        User user = userRepository.findByEmail(UserForm.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Not found Email"));

        // 비밀번호 검사
        if (!passwordEncoder.matches(UserForm.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Not Match Password");
        }

        // Access Token, Refresh Token 생성
        TokenDto tokenDto = jwtProvider.createAllToken(user.getEmail());

        // DB 에 Refresh Token 이 존재하는지 검사
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(user.getEmail());

        // DB 에 Refresh Token 이 존재하면 Refresh Token 을 삭제한다.
        if (refreshToken.isPresent()) {
            refreshTokenRepository.deleteById(refreshToken.get().getId());
        }

        // DB 에 Refresh Token 을 저장한다.
        refreshTokenRepository.save(RefreshToken.builder()
                .refreshToken(tokenDto.getRefreshToken())
                .email(user.getEmail())
                .build());

        // Response Header 에 Access Token, Refresh Token 을 추가한다.
        setHeader(response, tokenDto);

        return new ResponseDto("Success login", HttpStatus.OK.value());
    }

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

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.setHeader("Access-Token", tokenDto.getAccessToken());
        response.setHeader("Refresh-Token", tokenDto.getRefreshToken());
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
