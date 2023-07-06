package com.jr_devs.assemblog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post")

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "writer_mail")
    private String writerMail;

    private String title;

    private String content;

    private String thumbnail;

    @Column(name = "post_use_state", columnDefinition = "TINYINT(1)")
    private boolean postUseState;

    @Column(name = "comment_use_state", columnDefinition = "TINYINT(1)")
    private boolean commentUseState;

    @Column(name = "temp_save_state", columnDefinition = "TINYINT(1)")
    private boolean tempSaveState;

    private String preview;
}
