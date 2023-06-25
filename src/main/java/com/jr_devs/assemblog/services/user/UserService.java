package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.User;
import com.jr_devs.assemblog.models.UserDto;

public interface UserService {
    User join(UserDto UserForm);

    String login(UserDto UserForm);
}