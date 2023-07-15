package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.services.user.TokenService;
import com.jr_devs.assemblog.token.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        TokenDto tokenDto = tokenService.refresh(token.substring(7));

        if (tokenDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }
}
