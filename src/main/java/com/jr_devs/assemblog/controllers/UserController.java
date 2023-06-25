package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.UserDto;
import com.jr_devs.assemblog.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping({"/login"})
    public String login(@RequestBody UserDto userDto) {
        try {
            return this.userService.login(userDto);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping({"/signup"})
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        try {
            this.userService.join(userDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}