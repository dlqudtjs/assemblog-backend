package com.jr_devs.assemblog.model.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    private String title;

    @Column(name = "use_state", columnDefinition = "TINYINT(1)")
    private boolean useState;

    @Column(name = "order_num")
    private int orderNum;
}
