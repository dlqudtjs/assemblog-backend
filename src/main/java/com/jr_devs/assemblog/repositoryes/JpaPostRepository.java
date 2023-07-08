package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.models.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaPostRepository extends JpaRepository<Post, Long> {

    Post save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findByWriterMailAndTitle(String writerMail, String title);

    // post list 가져오기
    public static final String SELECT_ALL_POST_LIST = "SELECT * " +
            "FROM post " +
            "WHERE post_use_state = 1 AND temp_save_state = 0 " +
            "LIMIT :page_start_index, :page_size";

    @Query(value = "CALL get_post_list(:page_start_index, :page_size, :order, :order_type, :search_type)", nativeQuery = true)
    List<Post> findPostList(@Param("page_start_index") int pageStartIndex,
                            @Param("page_size") int pageSize,
                            @Param("order") String order,
                            @Param("order_type") String orderType,
                            @Param("search_type") int searchType);


    void deleteByTitle(String title);

    void deleteById(Long id);

    List<Post> findByWriterMailAndTempSaveState(String writerMail, boolean tempSaveState);

    Post findByWriterMailAndTitleAndTempSaveState(String writerMail, String title, boolean tempSaveState);
}
