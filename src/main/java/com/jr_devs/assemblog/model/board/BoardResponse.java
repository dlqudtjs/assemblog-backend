package com.jr_devs.assemblog.model.board;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {

    private Long id;

    private Long parentId;

    private String title;

    private boolean useState;

    private int orderNum;

    private int postCount;
}
