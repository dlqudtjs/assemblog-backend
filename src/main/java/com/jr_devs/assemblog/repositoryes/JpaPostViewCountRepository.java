package com.jr_devs.assemblog.repositoryes;

import com.jr_devs.assemblog.models.PostViewCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostViewCountRepository extends JpaRepository<PostViewCount, Long> {

    PostViewCount save(PostViewCount postViewCount);

    // 해당 게시글의 개수를 조회하는 쿼리
    int countByPostId(Long postId);
}
