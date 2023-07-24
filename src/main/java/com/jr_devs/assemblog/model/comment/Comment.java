package com.jr_devs.assemblog.model.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long postId;

    @Column(name = "writer_id")
    private Long writerId;

    private String nickname;

    private String content;

    private String password;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "created_at")
    @CreationTimestamp
    private String createdAt;

    @Column(name = "like_state", columnDefinition = "TINYINT(1)")
    private boolean likeState;

    @Column(name = "is_writer", columnDefinition = "TINYINT(1)")
    private boolean isWriter;

    @Column(name = "deleted", columnDefinition = "TINYINT(1)")
    private boolean deleted;
}
