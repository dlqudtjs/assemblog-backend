package com.jr_devs.assemblog.controller;

import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.tag.Tag;
import com.jr_devs.assemblog.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/api/tags")
    public ResponseEntity<String> createTag(@RequestBody List<String> tags) {
        try {
            ResponseDto responseDto = tagService.createTag(tags);
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

    @GetMapping("/lists/tags")
    public ResponseEntity<List<Tag>> readAllTags() {
        try {
            List<Tag> tags = tagService.readAllTags();
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
