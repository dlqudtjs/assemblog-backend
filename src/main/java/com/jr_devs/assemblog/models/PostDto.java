package com.jr_devs.assemblog.models;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @ColumnDefault("true")
    private boolean postUseState;
    private boolean commentUseState;
    private boolean tempSaveState;
    private String content;
    private String preview;
    // todo tag 기능 구현 후 주석 해제
//    private List<Tag> tags;
}
