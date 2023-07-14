package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.models.dtos.UserDto;
import com.jr_devs.assemblog.services.user.UserService;
import com.jr_devs.assemblog.token.TokenDto;
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
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        TokenDto tokenDto = userService.login(userDto);

        if (tokenDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }

    @PostMapping({"/signup"})
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        try {
            ResponseDto responseDto = userService.join(userDto);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}