package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.dtos.post.PostDto;
import com.jr_devs.assemblog.models.dtos.post.PostListResponseDto;
import com.jr_devs.assemblog.models.dtos.post.PostResponseDto;
import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.services.post.PostService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequiredArgsConstructor
public class PostController {

    private static final int SET_COOKIE_TIME = 60 * 60 * 24; // 1 day
    private static final String COOKIE_NAME = "assemblog_viewed_posts";

    private final PostService postService;

    @PostMapping("/api/posts")
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            ResponseDto responseDto;
            // 임시 저장
            if (postDto.isTempSaveState()) {
                responseDto = postService.tempSavePost(postDto, token.substring(7));
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
    public ResponseEntity<PostResponseDto> readPost(@PathVariable Long postId, HttpServletRequest request, HttpServletResponse response) {
        try {
            PostResponseDto postResponseDto = postService.readPost(postId);

            if (postResponseDto.getStatusCode() == HttpStatus.OK.value()) {
                // 조회수 증가
                viewCount(postId, request, response);
            }

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
            @RequestParam(required = false, defaultValue = "all") String boardTitle) {
        try {
            PostListResponseDto postListResponseDto = postService.readPostList(currentPage, pageSize, order, orderType, boardTitle);
            return ResponseEntity.status(postListResponseDto.getStatusCode()).body(postListResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/api/posts")
    public ResponseEntity<String> updatePost(@RequestBody PostDto postDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            ResponseDto responseDto = postService.updatePost(postDto, token.substring(7));
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            ResponseDto responseDto = postService.deletePost(postId, token.substring(7));
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void viewCount(Long postId, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        boolean isCookie = false;

        // request 에 쿠기가 있을 경우 확인한다.
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(COOKIE_NAME)) {
                    cookie = c;

                    if (!c.getValue().contains("[" + postId + "]")) {
                        postService.countView(postId);
                        c.setValue(c.getValue() + "[" + postId + "]");
                    }

                    isCookie = true;
                    break;
                }
            }
        }
        // request 에 postView 쿠키가 없을 경우 처음 접속한 것이므로 쿠키를 생성한다.
        if (!isCookie) {
            postService.countView(postId);
            cookie = new Cookie(COOKIE_NAME, "[" + postId + "]");
        }


        // 모든 경로에서 접근 가능
        cookie.setPath("/");
        cookie.setMaxAge(SET_COOKIE_TIME); // 1일
        response.addCookie(cookie);
    }
}
