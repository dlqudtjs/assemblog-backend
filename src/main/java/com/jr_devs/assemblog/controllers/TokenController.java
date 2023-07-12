package com.jr_devs.assemblog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {

    @GetMapping("/refresh")
    public String refresh(@RequestParam("authentication") String token) {
        return "refresh";
    }
}
