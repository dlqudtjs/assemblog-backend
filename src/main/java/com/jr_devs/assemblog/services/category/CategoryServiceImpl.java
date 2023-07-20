package com.jr_devs.assemblog.services.category;

import com.jr_devs.assemblog.models.board.Board;
import com.jr_devs.assemblog.models.category.Category;
import com.jr_devs.assemblog.models.category.CategoryDto;
import com.jr_devs.assemblog.models.dto.ResponseDto;
import com.jr_devs.assemblog.repositoryes.JpaCategoryRepository;
import com.jr_devs.assemblog.services.board.BoardService;
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

    @Transactional
    @Override
    public ResponseDto updateCategory(List<CategoryDto> categoryDtoList) {
        for (CategoryDto categoryDto : categoryDtoList) {
            Category category = categoryRepository.findById(categoryDto.getId()).orElse(null);

            if (category == null) {
                return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist category");
            }
            
            List<Category> categoryList = categoryRepository.findAllByTitle(categoryDto.getTitle());

            for (Category c : categoryList) {
                if (!c.getId().equals(categoryDto.getId())) {
                    return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate category title");
                }
            }

            category.setTitle(categoryDto.getTitle());
            category.setUseState(categoryDto.isUseState());
            category.setOrderNum(categoryDto.getOrderNum());
        }

        return createResponse(HttpStatus.OK.value(), "Success update category");
    }

    @Override
    public ResponseDto deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category == null && !category.isUseState()) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist category");
        }

        categoryRepository.deleteById(categoryId);

        // 카테고리 삭제 응답
        return createResponse(HttpStatus.OK.value(), "Success delete category");
    }

    @Override
    public String getCategoryTitle(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            return null;
        }

        return category.getTitle();
    }

    public List<CategoryDto> readAllCategoriesAndBoards() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        List<Category> categories = readAllCategories();

        for (Category category : categories) {
            // 카테고리 숨김 표시는 제외

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

    private ResponseDto createResponse(int statusCode, String message) {
        return ResponseDto.builder()
                .statusCode(statusCode)
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
