package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.User;
import com.jr_devs.assemblog.models.UserForm;
import com.jr_devs.assemblog.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping({"/login"})
    public void login() {
    }

    @PostMapping({"/signup"})
    public ResponseEntity<?> signup(UserForm userForm) {
        User user = new User();

        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(this.bCryptPasswordEncoder.encode(userForm.getPassword()));

        try {
            this.userService.join(user);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException var4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(var4.getMessage());
        }
    }
}