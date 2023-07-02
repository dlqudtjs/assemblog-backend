package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.models.Tag;
import com.jr_devs.assemblog.models.TagDto;
import com.jr_devs.assemblog.services.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/api/tags")
    public ResponseEntity<String> createTag(@RequestBody TagDto tagDto) {
        try {
            ResponseDto responseDto = tagService.createTag(tagDto);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/api/tags")
    public ResponseEntity<String> updateTag(@RequestBody TagDto tagDto) {
        try {
            ResponseDto responseDto = tagService.updateTag(tagDto);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/tags/{tagId}")
    public ResponseEntity<String> deleteTag(@PathVariable Long tagId) {
        try {
            ResponseDto responseDto = tagService.deleteTag(tagId);
            return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/tags")
    public ResponseEntity<List<Tag>> readAllTags() {
        try {
            List<Tag> tags = tagService.readAllTags();
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // todo 페이징 처리 구현하기
//    @GetMapping("/api/tags/{tagId}")
//    public ResponseEntity<Tag> readTag(@PathVariable Long tagId) {
//        try {
//            Tag tag = tagService.readTag(tagId);
//            return ResponseEntity.ok(tag);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
}
