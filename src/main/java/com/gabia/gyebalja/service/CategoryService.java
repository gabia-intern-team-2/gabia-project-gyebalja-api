package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.dto.category.CategoryRequestDto;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.exception.NotExistCategoryException;
import com.gabia.gyebalja.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /** 수정 - category 한 건 */
    @Transactional
    public Long putOneCategory(Long id, CategoryRequestDto categoryRequestDto) {
        Optional<Category> category = categoryRepository.findById(id); //1차 캐시에 저장

        if(!category.isPresent())
            throw new NotExistCategoryException("존재하지 않는 카테고리입니다.");

        category.get().changeCategoryName(categoryRequestDto.getName());

        return id;
    }

    /** 삭제 - category 한 건 */
    @Transactional
    public Long deleteOneCategory(Long id) {
        categoryRepository.deleteById(id);

        return id;
    }

    /** 조회 - category 전체 (전체조회) */ //해당 카테고리를 모두 뿌려줄거기때문에 페이징 불필요.
    public List<CategoryResponseDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtos = categories.stream()
                                                                    .map(c -> CategoryResponseDto.builder().id(c.getId()).name(c.getName()).build())
                                                                    .collect(Collectors.toList());// Entity를 노출 시키지않고 Dto로 변환후 리턴

        return categoryResponseDtos;
    }
}
