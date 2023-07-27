package com.jr_devs.assemblog.controller;

import com.jr_devs.assemblog.model.comment.CommentRequest;
import com.jr_devs.assemblog.model.comment.CommentListResponse;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<ResponseDto> createComment(@RequestBody CommentRequest commentRequest, @RequestHeader("Authorization") String token) {
        try {
            ResponseDto responseDto = commentService.createComment(commentRequest, token);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .statusCode(400)
                    .message(e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/comments")
    public ResponseEntity<String> deleteComment(@RequestParam Long id, @RequestParam String password, @RequestHeader("Authorization") String token) {
        try {
            ResponseDto responseDto = commentService.deleteComment(id, password, token);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/lists/comments")
    public ResponseEntity<List<CommentListResponse>> readAllComments(@RequestParam Long postId) {
        try {
            List<CommentListResponse> commentListResponses = commentService.readCommentList(postId);
            return ResponseEntity.ok(commentListResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/api/comments/likes/{commentId}")
    public ResponseEntity<String> likeComment(@PathVariable Long commentId) {
        try {
            ResponseDto responseDto = commentService.likeComment(commentId);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
