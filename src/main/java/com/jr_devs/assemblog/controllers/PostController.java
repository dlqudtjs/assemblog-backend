package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.PostDto;
import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.models.Tag;
import com.jr_devs.assemblog.services.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/posts")
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
        try {
            ResponseDto responseDto;
            // 임시 저장
            if (postDto.isTempSaveState()) {
                responseDto = postService.tempSavePost(postDto);
            } else {
                // 게시글 등록
                responseDto = postService.createPost(postDto);
            }
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/api/posts")
    public ResponseEntity<String> updatePost(@RequestBody PostDto postDto) {
        try {
            ResponseDto responseDto = postService.updatePost(postDto);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        try {
            ResponseDto responseDto = postService.deletePost(postId);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
