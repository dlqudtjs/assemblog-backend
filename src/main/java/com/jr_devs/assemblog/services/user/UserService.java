package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.models.dtos.UserDto;
import com.jr_devs.assemblog.token.TokenDto;

public interface UserService {
    ResponseDto join(UserDto UserForm);

    TokenDto login(UserDto UserForm);

    String getUsernameByEmail(String email);
}