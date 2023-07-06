package com.jr_devs.assemblog.services.post;

import com.jr_devs.assemblog.models.Post;
import com.jr_devs.assemblog.models.dtos.PostDto;
import com.jr_devs.assemblog.repositoryes.JpaPostRepository;
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

    private PostDto postDto;

    @BeforeEach
    public void setUp() {
        List<String> tags = new ArrayList<>();
        tags.add("test_tag1");
        tags.add("test_tag2");

        postDto = PostDto.builder()
                .boardId(4L)
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

    @Test
    @DisplayName("게시글 생성")
    public void createPost() {
        // when
        postService.createPost(postDto);

        // then
        List<Post> posts = postRepository.findByWriterMailAndTitle(postDto.getWriterMail(), postDto.getTitle());
        assertThat(posts.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("임시 저장 게시글 생성")
    public void createTempPost() {
        // given
        postDto.setTempSaveState(true);

        // when
        postService.createPost(postDto);

        // then
        List<Post> posts = postRepository.findByWriterMailAndTempSaveState(postDto.getWriterMail(), true);
        // posts 안에 postDto.getTitle()이 있는지 확인하는 테스트
        boolean isExist = false;
        for (Post post : posts) {
            if (post.getTitle().equals(postDto.getTitle())) {
                isExist = true;
                break;
            }
        }

        assertThat(isExist).isTrue();
    }

    @Test
    @DisplayName("임시 저장 게시글(제목) 중복 방지 테스트")
    public void createTempPostWithSameTitle() {
        // given
        postDto.setTempSaveState(true);
        postService.createPost(postDto);

        // when
        postDto.setContent("test_edit_content");
        postService.createPost(postDto);

        // then
        List<Post> posts = postRepository.findByWriterMailAndTempSaveState(postDto.getWriterMail(), true);
        boolean isExist = false;
        for (Post post : posts) {
            if (post.getContent().equals("test_edit_content")) {
                isExist = true;
                break;
            }
        }

        assertThat(isExist).isTrue();
    }
}
