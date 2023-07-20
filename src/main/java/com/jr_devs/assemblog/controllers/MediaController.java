package com.jr_devs.assemblog.controllers;

import com.jr_devs.assemblog.models.dto.MediaRequestDto;
import com.jr_devs.assemblog.services.media.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor

public class MediaController {

    private final UploadService uploadService;

    @PostMapping("/api/uploads/images")
    public ResponseEntity<String> uploadImage(MediaRequestDto mediaRequestDto) throws IOException {
        String path = uploadService.imageUpload(mediaRequestDto);

        if (path != null) {
            return ResponseEntity.ok(path);
        }

        return ResponseEntity.badRequest().body("failed to upload image");
    }
}
