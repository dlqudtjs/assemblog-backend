package com.jr_devs.assemblog.service.media;

import com.jr_devs.assemblog.model.dto.MediaRequestDto;

import java.io.IOException;

public interface UploadService {

    String imageUpload(MediaRequestDto mediaRequestDto) throws IOException;
}
