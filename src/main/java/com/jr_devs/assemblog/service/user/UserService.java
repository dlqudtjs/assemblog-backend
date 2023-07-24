package com.jr_devs.assemblog.service.user;

import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.user.UserResponse;
import com.jr_devs.assemblog.model.user.UserIntroductionResponse;
import com.jr_devs.assemblog.model.user.UserRequest;
import com.jr_devs.assemblog.model.user.UserUpdateDto;
import com.jr_devs.assemblog.token.TokenDto;

import java.util.List;

public interface UserService {
    ResponseDto join(UserRequest UserForm);

    TokenDto login(UserRequest UserForm);

    String getUsernameByEmail(String email);

    List<UserIntroductionResponse> getUserIntroductionList(String email);

    ResponseDto updateUserIntroduction(UserIntroductionResponse userIntroduction);

    UserResponse getUser(String token);

    ResponseDto updateUser(UserUpdateDto UserUpdateDto);
}