package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.BoardDto;
import com.jr_devs.assemblog.services.boards.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity<String> createBoard(@RequestBody BoardDto boardDto) {
        try {
            return ResponseEntity.ok(boardService.createBoard(boardDto).getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/api/boards")
    public ResponseEntity<String> updateBoard(@RequestBody BoardDto boardDto) {
        try {
            return ResponseEntity.ok(boardService.updateBoard(boardDto).getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/boards/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
        try {
            return ResponseEntity.ok(boardService.deleteBoard(boardId).getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
