package com.jr_devs.assemblog.models.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

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

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "post_use_state", columnDefinition = "TINYINT(1)")
    private boolean postUseState;

    @Column(name = "comment_use_state", columnDefinition = "TINYINT(1)")
    private boolean commentUseState;

    @Column(name = "temp_save_state", columnDefinition = "TINYINT(1)")
    private boolean tempSaveState;

    private String preview;
}
