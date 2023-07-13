package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.dtos.comment.CommentDto;
import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.models.dtos.comment.CommentListResponseDto;
import com.jr_devs.assemblog.services.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto) {
        try {
            ResponseDto responseDto = commentService.createComment(commentDto);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/comments")
    public ResponseEntity<String> deleteComment(@RequestParam Long id, @RequestParam String password) {
        try {
            ResponseDto responseDto = commentService.deleteComment(id, password);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/lists/comments")
    public ResponseEntity<CommentListResponseDto> readAllComments(@RequestParam Long postId) {
        try {
            System.out.println("postId: " + postId);
            CommentListResponseDto commentListResponseDto = commentService.readCommentList(postId);
            return ResponseEntity.status(commentListResponseDto.getStatusCode()).body(commentListResponseDto);
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
