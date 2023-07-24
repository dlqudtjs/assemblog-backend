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

    List<Post> findByWriterMailAndTitle(String writerMail, String title);

    @Query(value = "CALL get_post_list(:page_start_index, :page_size, :order, :order_type, :board_id, :tag_id)", nativeQuery = true)
    List<Post> findPostList(@Param("page_start_index") int pageStartIndex,
                            @Param("page_size") int pageSize,
                            @Param("order") String order,
                            @Param("order_type") String orderType,
                            @Param("board_id") long boardId,
                            @Param("tag_id") long tagId);

    @Query(value = "CALL get_post_count(:board_id, :tag_id)", nativeQuery = true)
    int findPostCount(@Param("board_id") long boardId,
                      @Param("tag_id") long tagId);

    void deleteByTitle(String title);

    void deleteById(Long id);

    List<Post> findByWriterMailAndTempSaveState(String writerMail, boolean tempSaveState);

    Post findByWriterMailAndTitleAndTempSaveState(String writerMail, String title, boolean tempSaveState);
}
