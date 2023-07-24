package com.jr_devs.assemblog.model.dto;

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
