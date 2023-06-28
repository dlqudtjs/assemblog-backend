package com.jr_devs.assemblog.services.category;

import com.jr_devs.assemblog.models.Category;
import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.repositoryes.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final JpaCategoryRepository categoryRepository;

    @Override
    public ResponseDto createCategory(String title) {
        if (checkDuplicate(title)) {
            return ResponseDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message("Duplicate category title")
                    .build();
        }

        int order = categoryOrder().intValue() + 1;

        // 카테고리 생성
        categoryRepository.save(Category.builder()
                .title(title)
                .useState(true)
                .order(order)
                .build());

        // 카테고리 생성 응답
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success create category")
                .build();
    }

    private Long categoryOrder() {
        return categoryRepository.countBy();
    }

    // 카테고리명 중복 체크
    private Boolean checkDuplicate(String title) {
        return categoryRepository.existsByTitle(title);
    }
}
