package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.models.User;
import com.jr_devs.assemblog.models.UserDto;
import com.jr_devs.assemblog.token.RefreshToken;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    ResponseDto join(UserDto UserForm);

    ResponseDto login(UserDto UserForm, HttpServletResponse response);
}