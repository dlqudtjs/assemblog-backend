package com.jr_devs.assemblog.model.guestbook;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "guestbook")
public class Guestbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "writer_id")
    private Long writerId;

    private String nickname;

    private String content;

    private String password;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private String createdAt;

    @Column(name = "like_state")
    private boolean likeState;

    @Column(name = "is_writer")
    private boolean isWriter;

    private boolean deleted;
}
