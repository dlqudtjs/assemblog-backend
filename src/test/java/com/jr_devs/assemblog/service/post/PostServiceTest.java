package com.jr_devs.assemblog.service.post;

import com.jr_devs.assemblog.model.post.Post;
import com.jr_devs.assemblog.model.post.PostRequest;
import com.jr_devs.assemblog.repository.JpaPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private JpaPostRepository postRepository;

    private PostRequest postDto;

    @BeforeEach
    public void setUp() {
        List<String> tags = new ArrayList<>();
        tags.add("test_tag1");
        tags.add("test_tag2");

        postDto = PostRequest.builder()
                .boardId(5L)
                .writerMail("user@gmail.com")
                .title("test_title")
                .content("test_content")
                .thumbnail("test_thumbnail")
                .postUseState(true)
                .commentUseState(true)
                .tempSaveState(false)
                .preview("test_preview")
                .tags(tags)
                .build();
    }
}
