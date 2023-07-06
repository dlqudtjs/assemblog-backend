package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaPostRepository extends JpaRepository<Post, Long> {

    Post save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findByWriterMailAndTitle(String writerMail, String title);

    void deleteByTitle(String title);

    void deleteById(Long id);

    List<Post> findByWriterMailAndTempSaveState(String writerMail, boolean tempSaveState);

    Post findByWriterMailAndTitleAndTempSaveState(String writerMail, String title, boolean tempSaveState);
}
