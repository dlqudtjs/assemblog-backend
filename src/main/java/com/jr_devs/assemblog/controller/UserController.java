package com.jr_devs.assemblog.controller;

import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.user.UserResponse;
import com.jr_devs.assemblog.model.user.UserIntroductionResponse;
import com.jr_devs.assemblog.model.user.UserRequest;
import com.jr_devs.assemblog.model.user.UserUpdateDto;
import com.jr_devs.assemblog.service.user.UserService;
import com.jr_devs.assemblog.token.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
//@RequestMapping(value = "/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping({"/users/login"})
    public ResponseEntity<TokenDto> login(@RequestBody UserRequest UserRequest) {
        TokenDto tokenDto = userService.login(UserRequest);

        if (tokenDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }

    @PostMapping({"/users/signup"})
    public ResponseEntity<String> signup(@RequestBody UserRequest UserRequest) {
        try {
            ResponseDto responseDto = userService.join(UserRequest);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping({"/api/users"})
    public ResponseEntity<UserResponse> getUser(@RequestHeader("Authorization") String token) {
        try {
            UserResponse userDto = userService.getUser(token.substring(7));
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PatchMapping({"/api/users"})
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDto UserUpdateDto) {
        try {
            ResponseDto responseDto = userService.updateUser(UserUpdateDto);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PatchMapping({"/api/user-introductions"})
    public ResponseEntity<String> updateUserIntroduction(@RequestBody UserIntroductionResponse UserIntroduction) {
        try {
            ResponseDto responseDto = userService.updateUserIntroduction(UserIntroduction);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/lists/user-introductions")
    public ResponseEntity<List<UserIntroductionResponse>> getUserIntroductionList(
            @RequestParam(required = false, defaultValue = "") String email
    ) {
        List<UserIntroductionResponse> users = userService.getUserIntroductionList(email);

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}