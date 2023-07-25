package com.jr_devs.assemblog.service.tag;

import com.jr_devs.assemblog.repository.JpaTagRepository;
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


}
