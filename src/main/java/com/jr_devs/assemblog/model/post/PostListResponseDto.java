package com.jr_devs.assemblog.model.post;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponseDto {

    private List<PostListResponse> postList;
    private int totalPage;
    private int currentPage;

    // for error
    int statusCode;
    String message;
}
