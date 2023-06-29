package com.jr_devs.assemblog.services.category;

import com.jr_devs.assemblog.models.Board;
import com.jr_devs.assemblog.models.Category;
import com.jr_devs.assemblog.models.CategoryDto;
import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.repositoryes.JpaCategoryRepository;
import com.jr_devs.assemblog.services.boards.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final JpaCategoryRepository categoryRepository;
    private final BoardService boardService;

    @Override
    public ResponseDto createCategory(CategoryDto categoryDto) {
        if (checkDuplicate(categoryDto.getTitle())) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate category title");
        }

        int order = getCategoryOrder().intValue() + 1;

        // 카테고리 생성
        categoryRepository.save(Category.builder()
                .title(categoryDto.getTitle())
                .useState(true)
                .orderNum(order)
                .build());

        // 카테고리 생성 응답
        return createResponse(HttpStatus.OK.value(), "Success create category");
    }

    @Override
    public List<Category> readAllCategories() {
        return categoryRepository.findAllByOrderByOrderNumAsc();
    }

    @Override
    public ResponseDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElse(null);

        if (category == null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist category");
        }

        category.setTitle(categoryDto.getTitle());
        category.setUseState(categoryDto.isUseState());
        category.setOrderNum(categoryDto.getOrderNum());

        return createResponse(HttpStatus.OK.value(), "Success update category");
    }

    @Override
    public ResponseDto deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category == null && !category.isUseState()) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist category");
        }

        // 카테고리 삭제
        // JPA 는 트랜잭션이 끝나는 시점에 변경사항을 DB에 반영한다. (삭제대신 사용유무를 false 로 변경)
        category.setUseState(false);

        // 카테고리 삭제 응답
        return createResponse(HttpStatus.OK.value(), "Success delete category");
    }

    public List<CategoryDto> readAllCategoriesAndBoards() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        List<Category> categories = readAllCategories();

        for (Category category : categories) {
            // 카테고리 숨김 표시는 제외
            if (!category.isUseState()) continue;

            List<Board> boards = boardService.readAllByParentId(category.getId());

            categoryDtoList.add(CategoryDto.builder()
                    .id(category.getId())
                    .title(category.getTitle())
                    .useState(category.isUseState())
                    .orderNum(category.getOrderNum())
                    .boards(boards)
                    .build());
        }

        return categoryDtoList;
    }

    private ResponseDto createResponse(int value, String message) {
        return ResponseDto.builder()
                .statusCode(value)
                .message(message)
                .build();
    }

    private Long getCategoryOrder() {
        return categoryRepository.countBy();
    }

    // 카테고리명 중복 체크
    private Boolean checkDuplicate(String title) {
        return categoryRepository.existsByTitle(title);
    }
}
