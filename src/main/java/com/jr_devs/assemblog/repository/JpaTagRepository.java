package com.jr_devs.assemblog.repository;

import com.jr_devs.assemblog.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaTagRepository extends JpaRepository<Tag, Long> {

    Tag save(Tag tag);

    Tag findByName(String name);

    void deleteById(Long id);

    // 임시 저장, 숨김 여부를 체크 하지 않은 게시글을 참조한 태그만 조회
    @Query(value = "CALL get_tag_list()", nativeQuery = true)
    List<Tag> findAllTags();
}
