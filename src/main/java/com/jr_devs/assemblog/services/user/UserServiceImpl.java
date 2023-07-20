package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.dto.ResponseDto;
import com.jr_devs.assemblog.models.user.*;
import com.jr_devs.assemblog.repositoryes.JpaRefreshTokenRepository;
import com.jr_devs.assemblog.repositoryes.JpaUserIntroductionLinkRepository;
import com.jr_devs.assemblog.repositoryes.JpaUserIntroductionRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JpaUserRepository userRepository;
    private final JpaUserIntroductionRepository userIntroductionRepository;
    private final JpaUserIntroductionLinkRepository userIntroductionLinkRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JpaRefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public TokenDto login(UserDto userDto) {
        // 이메일 검사
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Not found Email"));

        // 비밀번호 검사
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Not Match Password");
        }

        // Access Token, Refresh Token 생성
        TokenDto tokenDto = jwtProvider.createAllToken(userDto.getEmail());

        // DB 에 Refresh Token 이 존재하는지 검사
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(userDto.getEmail());

        // DB 에 Refresh Token 이 존재하면 Refresh Token 을 삭제한다.
        if (refreshToken.isPresent()) {
            refreshTokenRepository.deleteByEmail(userDto.getEmail());
        }

        // todo (정리하기) jpa 는 작동 순서가 정해져 있어 delete 후 바로 save 를 하면 delete 가 먼저 실행되지 않는다. 따라서 오류가 발생함
        refreshTokenRepository.flush();

        // DB 에 Refresh Token 을 저장한다.
        refreshTokenRepository.save(RefreshToken.builder()
                .refreshToken(tokenDto.getRefresh_token())
                .email(userDto.getEmail())
                .build());

        return tokenDto;
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
        User createdUser = userRepository.save(user);

        // 회원가입 시 유저 소개글 생성
        userIntroductionRepository.save(UserIntroduction.builder()
                .userId(createdUser.getId())
                .introduction(null)
                .build());

        // 회원가입 성공시 응답
        return ResponseDto.builder()
                .message("Success signup")
                .statusCode(HttpStatus.OK.value()) // int 형이기 때문에 value()를 사용해야 한다.
                .build();
    }

    @Override
    public ResponseDto updateUserIntroduction(UserIntroductionResponse userIntroductionDto) {
        User user = userRepository.findByEmail(userIntroductionDto.getEmail()).get();

        if (user == null) {
            return ResponseDto.builder()
                    .message("Not found user")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        UserIntroduction findUserIntroduction = userIntroductionRepository.findByUserId(user.getId());

        findUserIntroduction.setIntroduction(userIntroductionDto.getIntroduction());
        findUserIntroduction.setProfileImageURL(userIntroductionDto.getProfileImageURL());

        List<UserIntroductionLink> userIntroductionLinkList = userIntroductionLinkRepository.findByUserId(findUserIntroduction.getId());

        // 유저 소개 링크가 존재하면 삭제한다.
        for (UserIntroductionLink userIntroductionLink : userIntroductionLinkList) {
            userIntroductionLinkRepository.deleteById(userIntroductionLink.getId());
        }

        // 유저 소개 링크를 저장한다.
        for (UserIntroductionLink userIntroductionLink : userIntroductionDto.getLinks()) {
            userIntroductionLinkRepository.save(UserIntroductionLink.builder()
                    .userId(findUserIntroduction.getUserId())
                    .linkDescription(userIntroductionLink.getLinkDescription())
                    .linkURL(userIntroductionLink.getLinkURL())
                    .linkImageURL(userIntroductionLink.getLinkImageURL())
                    .build());
        }

        return ResponseDto.builder()
                .message("Success update user introduction")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public List<UserIntroductionResponse> getUserIntroductionList(String email) {
        // filter(email) 이 0일 땐 전체 유저의 소개를 가져온다. (email 이 있으면 해당 유저의 소개를 가져온다.)
        Long userId = email.equals("") ? 0L : userRepository.findByEmail(email).get().getId();

        List<UserIntroduction> userIntroductionList = userIntroductionRepository.findUserIntroductionList(userId);

        List<UserIntroductionResponse> userIntroductionResponseList = new ArrayList<>();

        for (UserIntroduction userIntroduction : userIntroductionList) {
            User user = userRepository.findById(userIntroduction.getUserId()).get();
            List<UserIntroductionLink> userIntroductionLinkList = userIntroductionLinkRepository.findByUserId(userIntroduction.getId());

            userIntroductionResponseList.add(UserIntroductionResponse.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .introduction(userIntroduction.getIntroduction())
                    .profileImageURL(userIntroduction.getProfileImageURL())
                    .links(userIntroductionLinkList)
                    .build());
        }

        return userIntroductionResponseList;
    }

    @Override
    public String getUsernameByEmail(String email) {
        return userRepository.findByEmail(email).get().getUsername();
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
