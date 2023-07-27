package com.jr_devs.assemblog.service.board;

import com.jr_devs.assemblog.model.board.Board;
import com.jr_devs.assemblog.model.category.Category;
import com.jr_devs.assemblog.model.board.BoardRequest;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.repository.JpaBoardRepository;
import com.jr_devs.assemblog.repository.JpaCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private JpaBoardRepository boardRepository;

    @Autowired
    private JpaCategoryRepository categoryRepository;

    Category category;

    @BeforeEach
    public void init() {
        String categoryTitle = "test_category";
        int order = Integer.MAX_VALUE;

        category = categoryRepository.save(Category.builder()
                .title(categoryTitle)
                .useState(true)
                .orderNum(order)
                .build());
    }
}
