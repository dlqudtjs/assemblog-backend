package com.jr_devs.assemblog.models;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaRequestDto {

    private String email;
    private MultipartFile file;
}
