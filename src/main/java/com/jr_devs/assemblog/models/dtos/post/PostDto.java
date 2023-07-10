package com.jr_devs.assemblog.models.dtos.post;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    private Long boardId;

    private String writerMail;

    private String title;

    private String content;

    private String thumbnail;

    private boolean postUseState;

    private boolean commentUseState;

    private boolean tempSaveState;

    private String preview;

    private List<String> tags;
}