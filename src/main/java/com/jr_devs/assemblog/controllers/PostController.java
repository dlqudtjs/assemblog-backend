package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.dtos.PostDto;
import com.jr_devs.assemblog.models.dtos.PostListResponseDto;
import com.jr_devs.assemblog.models.dtos.PostResponseDto;
import com.jr_devs.assemblog.models.dtos.ResponseDto;
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

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> readPost(@PathVariable Long postId) {
        try {
            PostResponseDto postResponseDto = postService.readPost(postId);
            return ResponseEntity.status(postResponseDto.getStatusCode()).body(postResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/lists/posts")
    public ResponseEntity<PostListResponseDto> readPostList(
            @RequestParam(required = false, defaultValue = "1") int currentPage,
            @RequestParam(required = false, defaultValue = "15") int pageSize,
            @RequestParam(required = false, defaultValue = "created_at") String order,
            @RequestParam(required = false, defaultValue = "desc") String orderType,
            @RequestParam(required = false, defaultValue = "0") int searchType) {
        try {
            PostListResponseDto postListResponseDto = postService.readPostList(currentPage, pageSize, order, orderType, searchType);
            return ResponseEntity.status(postListResponseDto.getStatusCode()).body(postListResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
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
