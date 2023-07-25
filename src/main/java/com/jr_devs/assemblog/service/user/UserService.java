package com.jr_devs.assemblog.service.user;

import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.user.UserResponse;
import com.jr_devs.assemblog.model.user.UserIntroductionResponse;
import com.jr_devs.assemblog.model.user.UserRequest;
import com.jr_devs.assemblog.model.user.UserUpdateRequest;
import com.jr_devs.assemblog.token.TokenDto;

import java.util.List;

public interface UserService {
    ResponseDto join(UserRequest userRequest);

    TokenDto login(UserRequest userRequest);

    String getUsernameByEmail(String email);

    List<UserIntroductionResponse> getUserIntroductionList(String email);

    ResponseDto updateUserIntroduction(UserIntroductionResponse userIntroduction);

    UserResponse getUser(String token);

    ResponseDto updateUser(UserUpdateRequest userUpdateRequest);
}