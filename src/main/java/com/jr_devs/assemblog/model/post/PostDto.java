package com.jr_devs.assemblog.model.post;

import lombok.*;

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
