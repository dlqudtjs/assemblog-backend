package com.jr_devs.assemblog.model.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post_tag")
public class PostTag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "tag_id")
    private Long tagId;
}
