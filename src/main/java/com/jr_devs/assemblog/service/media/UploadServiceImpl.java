package com.jr_devs.assemblog.service.media;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.jr_devs.assemblog.model.dto.MediaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Value("${gcp.bucket.image.name}")
    private String imagePath;

    private final Storage storage;

    @Override
    public String imageUpload(MediaRequestDto mediaRequestDto) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String extension = mediaRequestDto.getFile().getContentType();

        String objectName = imagePath + mediaRequestDto.getEmail() + uuid;

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, objectName)
                        .setContentType(extension)
                        .build(),
                mediaRequestDto.getFile().getBytes());

        return blobInfo.getMediaLink();
    }
}
