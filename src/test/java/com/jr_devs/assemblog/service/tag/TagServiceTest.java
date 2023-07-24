package com.jr_devs.assemblog.service.tag;

import com.jr_devs.assemblog.model.tag.TagDto;
import com.jr_devs.assemblog.repository.JpaTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TagServiceTest {

    @Autowired
    private JpaTagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @Test
    @DisplayName("Tag 생성 테스트")
    public void createTagTest() {
        // given
        String name = "test_tag";

        // when
        tagService.createTag(TagDto.builder()
                .name(name)
                .build());

        // then
        assertThat(tagRepository.findByName(name)).isNotNull();
    }

    @Test
    @DisplayName("Tag 삭제 테스트")
    public void deleteTagTest() {
        // given
        String name = "test_tag";
        tagService.createTag(TagDto.builder()
                .name(name)
                .build());

        // when
        tagService.deleteTag(tagRepository.findByName(name).getId());

        // then
        assertThat(tagRepository.findByName(name)).isNull();
    }
}
