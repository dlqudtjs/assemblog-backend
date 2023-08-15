package com.jr_devs.assemblog.model.category;

import com.jr_devs.assemblog.model.board.Board;
import com.jr_devs.assemblog.model.board.BoardResponse;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;

    private String title;

    @ColumnDefault("true")
    private boolean useState;

    private int orderNum;

    private int boardCount;

    private List<BoardResponse> boards;
}
