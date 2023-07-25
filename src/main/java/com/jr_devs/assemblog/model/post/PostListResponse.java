package com.jr_devs.assemblog.model.post;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponse {

    private List<PostListResponseDto> postList;
    private int totalPage;
    private int currentPage;
}
