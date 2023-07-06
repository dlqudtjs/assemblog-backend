package com.jr_devs.assemblog.models.dtos;

import com.jr_devs.assemblog.models.Board;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    private String title;

    @ColumnDefault("true")
    private boolean useState;

    private int orderNum;

    private List<Board> boards;
}
