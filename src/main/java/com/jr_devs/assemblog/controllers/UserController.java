package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.dto.ResponseDto;
import com.jr_devs.assemblog.models.user.UserDto;
import com.jr_devs.assemblog.models.user.UserIntroductionResponse;
import com.jr_devs.assemblog.services.user.UserService;
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
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        TokenDto tokenDto = userService.login(userDto);

        if (tokenDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }

    @PostMapping({"/users/signup"})
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        try {
            ResponseDto responseDto = userService.join(userDto);
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