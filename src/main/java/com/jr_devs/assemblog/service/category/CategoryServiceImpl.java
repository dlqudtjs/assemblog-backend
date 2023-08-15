package com.jr_devs.assemblog.service.category;

import com.jr_devs.assemblog.model.board.Board;
import com.jr_devs.assemblog.model.board.BoardResponse;
import com.jr_devs.assemblog.model.category.Category;
import com.jr_devs.assemblog.model.category.CategoryRequest;
import com.jr_devs.assemblog.model.category.CategoryResponse;
import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.repository.JpaCategoryRepository;
import com.jr_devs.assemblog.service.board.BoardService;
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
    public ResponseDto createCategory(CategoryRequest categoryRequest) {
        if (checkDuplicate(categoryRequest.getTitle())) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate category title");
        }

        int order = getCategoryOrder().intValue() + 1;

        // 카테고리 생성
        categoryRepository.save(Category.builder()
                .title(categoryRequest.getTitle())
                .useState(true)
                .orderNum(order)
                .build());

        // 카테고리 생성 응답
        return createResponse(HttpStatus.OK.value(), "Success create category");
    }

    @Transactional
    @Override
    public ResponseDto updateCategory(List<CategoryRequest> categoryRequestList) {
        for (CategoryRequest categoryRequest : categoryRequestList) {
            Category category = categoryRepository.findById(categoryRequest.getId()).orElse(null);

            if (category == null) {
                return createResponse(HttpStatus.BAD_REQUEST.value(), "Not exist category");
            }

            List<Category> categoryList = categoryRepository.findAllByTitle(categoryRequest.getTitle());

            for (Category c : categoryList) {
                if (!c.getId().equals(categoryRequest.getId())) {
                    return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate category title");
                }
            }

            category.setTitle(categoryRequest.getTitle());
            category.setUseState(categoryRequest.isUseState());
            category.setOrderNum(categoryRequest.getOrderNum());
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
    public List<Category> readAllCategories() {
        return categoryRepository.findAllByOrderByOrderNumAsc();
    }

    public List<CategoryResponse> readAllCategoriesAndBoards() {
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        List<Category> categories = readAllCategories();

        for (Category category : categories) {
            List<Board> boards = boardService.readAllByParentId(category.getId());

            int postCount = 0;
            List<BoardResponse> boardResponseList = new ArrayList<>();
            for (Board board : boards) {
                boardResponseList.add(BoardResponse.builder()
                        .id(board.getId())
                        .parentId(board.getParentId())
                        .title(board.getTitle())
                        .useState(board.isUseState())
                        .orderNum(board.getOrderNum())
                        .postCount(boardService.getPostCount(board.getId()))
                        .build());

                postCount += boardService.getPostCount(board.getId());
            }

            categoryResponseList.add(CategoryResponse.builder()
                    .id(category.getId())
                    .title(category.getTitle())
                    .useState(category.isUseState())
                    .orderNum(category.getOrderNum())
                    .boardCount(postCount)
                    .boards(boardResponseList)
                    .build());
        }

        return categoryResponseList;
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
