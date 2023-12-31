package com.jr_devs.assemblog.model.board;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {

    private Long id;

    private Long parentId;

    private String title;

    private boolean useState;

    private int orderNum;
}
