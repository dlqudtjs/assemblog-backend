package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.models.post.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPostTagRepository extends JpaRepository<PostTag, Long> {

    PostTag save(PostTag postTag);

    void deleteById(Long id);

    PostTag findByPostIdAndTagId(Long postId, Long tagId);

    List<PostTag> findAllByPostId(Long postId);

    List<PostTag> findAllByTagId(Long tagId);
}
