package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.services.user.TokenService;
import com.jr_devs.assemblog.token.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestParam("authentication") String token) {
        try {
            TokenDto tokenDto = tokenService.refresh(token);
            return ResponseEntity.status(HttpStatus.OK).body(tokenDto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
