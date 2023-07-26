package com.jr_devs.assemblog.controller;

import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.guestbook.GuestbookListResponse;
import com.jr_devs.assemblog.model.guestbook.GuestbookRequest;
import com.jr_devs.assemblog.service.guestbook.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    @PostMapping("/guestbooks")
    public ResponseEntity<String> createGuestbook(@RequestBody GuestbookRequest guestbookRequest, @RequestHeader("Authorization") String token) {
        try {
            ResponseDto responseDto = guestbookService.createGuestbook(guestbookRequest, token);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/lists/guestbooks")
    public ResponseEntity<List<GuestbookListResponse>> readAllGuestbooks() {
        try {
            ResponseDto responseDto = guestbookService.readGuestbookList();
            return ResponseEntity.ok((List<GuestbookListResponse>) responseDto.getData());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/guestbooks")
    public ResponseEntity<String> deleteGuestbook(@RequestParam Long id, @RequestParam String password, @RequestHeader("Authorization") String token) {
        try {
            ResponseDto responseDto = guestbookService.deleteGuestbook(id, password, token);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("api/guestbooks/likes/{guestbookId}")
    public ResponseEntity<String> likeGuestbook(@PathVariable Long guestbookId) {
        try {
            ResponseDto responseDto = guestbookService.likeGuestbook(guestbookId);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
