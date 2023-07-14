package com.jr_devs.assemblog.models;

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

    private String nickname;

    private String content;

    @Column(name = "parent_comment_id")
    private int parentCommentId;

    private int depth;

    private String password;

    @Column(name = "created_at")
    @CreationTimestamp
    private String createdAt;

    @Column(name = "like_state", columnDefinition = "TINYINT(1)")
    private boolean likeState;
}
