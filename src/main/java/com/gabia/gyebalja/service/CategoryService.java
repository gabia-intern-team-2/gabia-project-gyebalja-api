package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.dto.category.CategoryRequestDto;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.exception.NotExistCategoryException;
import com.gabia.gyebalja.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //dto 에 valid 걸어보기
    /** 등록 - category 한 건 (카테고리 등록) */
    @Transactional
    public Long postOneCategory(CategoryRequestDto categoryRequestDto) {
        Long categoryId = categoryRepository.save(Category.builder()
                                                    .name(categoryRequestDto.getName())
                                                    .build()).getId();

        return categoryId;
    }

    /** 조회 - category 한 건 */
    public CategoryResponseDto getOneCategory(Long id) {
        Optional<Category> findCategory = categoryRepository.findById(id);

        if(!findCategory.isPresent())
            throw new NotExistCategoryException("존재하지 않는 카테고리입니다.");

        return CategoryResponseDto.builder()
                                    .id(findCategory.get().getId())
                                    .name(findCategory.get().getName())
                                    .build();
    }
}
