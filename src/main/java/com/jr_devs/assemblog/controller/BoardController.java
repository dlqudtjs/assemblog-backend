package com.jr_devs.assemblog.controller;

import com.jr_devs.assemblog.model.board.BoardDto;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity<String> createBoard(@RequestBody BoardDto boardDto) {
        try {
            ResponseDto responseDto = boardService.createBoard(boardDto);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/api/boards")
    public ResponseEntity<String> updateBoard(@RequestBody List<BoardDto> boardDtoList) {
        try {
            ResponseDto responseDto = boardService.updateBoard(boardDtoList);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/boards/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
        try {
            ResponseDto responseDto = boardService.deleteBoard(boardId);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
