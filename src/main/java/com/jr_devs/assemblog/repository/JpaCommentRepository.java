package com.jr_devs.assemblog.repository;


import com.jr_devs.assemblog.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    void deleteById(Long id);

    Optional<Comment> findById(Long id);

    List<Comment> findAllById(Long id);

    List<Comment> findAllByPostId(Long postId);

    int countAllByPostId(Long postId);

    int countAllByParentCommentId(Long parentCommentId);

    void flush();
}
