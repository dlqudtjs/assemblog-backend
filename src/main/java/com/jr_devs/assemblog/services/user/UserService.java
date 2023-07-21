package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.dto.ResponseDto;
import com.jr_devs.assemblog.models.user.UserDto;
import com.jr_devs.assemblog.models.user.UserIntroductionResponse;
import com.jr_devs.assemblog.models.user.UserUpdateDto;
import com.jr_devs.assemblog.token.TokenDto;

import java.util.List;

public interface UserService {
    ResponseDto join(UserDto UserForm);

    TokenDto login(UserDto UserForm);

    String getUsernameByEmail(String email);

    List<UserIntroductionResponse> getUserIntroductionList(String email);

    ResponseDto updateUserIntroduction(UserIntroductionResponse userIntroduction);

    UserDto getUser(String token);

    ResponseDto updateUser(UserUpdateDto UserUpdateDto);
}