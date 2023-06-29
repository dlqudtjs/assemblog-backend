package com.jr_devs.assemblog.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Long id;

    private Long parentId;

    private String title;

    private boolean useState;

    private int orderNum;
}
