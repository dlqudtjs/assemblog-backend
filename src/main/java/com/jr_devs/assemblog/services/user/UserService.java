package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.models.dtos.UserDto;

public interface UserService {
    ResponseDto join(UserDto UserForm);

    ResponseDto login(UserDto UserForm);

    String getUsernameByEmail(String email);
}