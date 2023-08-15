package com.jr_devs.assemblog.repository;

import com.jr_devs.assemblog.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaPostRepository extends JpaRepository<Post, Long> {

    Post save(Post post);

    Optional<Post> findById(Long id);

    @Query(value = "CALL get_post_list(:board_id, :search_word, :tag_id)", nativeQuery = true)
    List<Post> findPostList(@Param("board_id") long boardId,
                            @Param("search_word") String searchWord,
                            @Param("tag_id") long tagId);

    @Query(value = "CALL get_post_count(:board_id, :tag_id, :search_word)", nativeQuery = true)
    int findPostCount(@Param("board_id") long boardId,
                      @Param("tag_id") long tagId,
                      @Param("search_word") String searchWord);

    void deleteByTitle(String title);

    void deleteById(Long id);

    int countByBoardId(Long boardId);

    List<Post> findByWriterMailAndTempSaveState(String writerMail, boolean tempSaveState);

    Post findByWriterMailAndTitleAndTempSaveState(String writerMail, String title, boolean tempSaveState);
}
