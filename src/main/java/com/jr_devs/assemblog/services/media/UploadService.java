package com.jr_devs.assemblog.services.media;

import com.jr_devs.assemblog.models.MediaRequestDto;

import java.io.IOException;

public interface UploadService {

    String imageUpload(MediaRequestDto mediaRequestDto) throws IOException;
}
