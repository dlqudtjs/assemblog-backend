package com.jr_devs.assemblog.repositoryes;


import com.jr_devs.assemblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    void deleteById(Long id);

    Optional<Comment> findById(Long id);

    List<Comment> findAllByPostId(Long postId);
}
