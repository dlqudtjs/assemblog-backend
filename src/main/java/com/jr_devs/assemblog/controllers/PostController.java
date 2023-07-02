package com.jr_devs.assemblog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    @PostMapping("/api/posts")
    public ResponseEntity<String> createPost() {
        return null;
    }
}
