package com.jr_devs.assemblog.models.post;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long postId;
    private String username;
    private String writerMail;
    private String title;
    private String content;
    private String thumbnail;
    private Date createdAt;
    private Date updatedAt;
    private boolean postUseState;
    private boolean commentUseState;
    private boolean tempSaveState;
    private String boardTitle;
    private String categoryTitle;
    private int viewCount;
    private List<String> tagList;

    // for error
    private int statusCode;
    private String message;
}