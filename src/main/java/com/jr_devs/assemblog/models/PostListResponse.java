package com.jr_devs.assemblog.models;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListResponse {

    private Long postId;
    private String username;
    private String title;
    private String thumbnail;
    private String preview;
    private Date createdAt;
    private Date updatedAt;
    private String categoryTitle;
    private String boardTitle;
    private int viewCount;
    private int likeCount;
    private int commentCount;
}
