package com.gabia.gyebalja.category;

import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.dto.category.CategoryRequestDto;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : 정태균
 * Part : All
 */

@Transactional
@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
public class CategoryServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * 등록 - category 한 건 (단건 등록)
     */
    @Test
    @DisplayName("CategoryService.postOneCategory() 테스트 (단건 저장)")
    public void postOneCateogry() throws Exception {
        //given
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .name("개발")
                .build();
        //when
        Long categoryId = categoryService.postOneCategory(categoryRequestDto);
        em.clear();
        Category findCategory = categoryRepository.findById(categoryId).get();

        //then
        assertThat(categoryId).isEqualTo(findCategory.getId());
        assertThat(categoryRequestDto.getName()).isEqualTo(findCategory.getName());
    }

    /**
     * 조회 - category 한 건 (단건 조회)
     */
    @Test
    @DisplayName("CategoryService.getOneCategory() 테스트 (단건 조회)")
    public void getOneCategory() throws Exception {
        //given
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .name("개발")
                .build();
        Long categoryId = categoryService.postOneCategory(categoryRequestDto);
        em.clear();

        //when
        CategoryResponseDto findCategory = categoryService.getOneCategory(categoryId);

        //then
        assertThat(findCategory.getId()).isEqualTo(categoryId);
        assertThat(findCategory.getName()).isEqualTo(categoryRequestDto.getName());
    }

    /**
     * 수정 - category 한 건 (단건 수정)
     */
    @Test
    @DisplayName("CategoryService.putOneCategory() 테스트 (단건 수정)")
    public void putOneCategory() throws Exception {
        //given
        String updateName = "기획";
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .name("개발")
                .build();
        Long categoryId = categoryService.postOneCategory(categoryRequestDto);

        //when
        categoryRequestDto.setName(updateName);
        Long updateId = categoryService.putOneCategory(categoryId, categoryRequestDto);
        em.flush(); //업데이트 쿼리를 보기위함.
        Category findUpdateCategory = categoryRepository.findById(updateId).get();

        //then
        assertThat(findUpdateCategory.getId()).isEqualTo(categoryId);
        assertThat(findUpdateCategory.getName()).isEqualTo(updateName);
    }

    /**
     * 삭제 - category 한 건(단건 삭제)
     */
    @Test
    @DisplayName("CategoryService.deleteOneCategory() 테스트 (단건 삭제)")
    public void deleteOneCategory() throws Exception {
        //given
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .name("개발")
                .build();
        Long categoryId = categoryService.postOneCategory(categoryRequestDto);
        long beforeDeleteCnt = categoryRepository.count();

        //when
        Long deleteId = categoryService.deleteOneCategory(categoryId);

        //then
        assertThat(categoryRepository.count()).isEqualTo(beforeDeleteCnt-1);
        assertThat(categoryRepository.findById(deleteId)).isEqualTo(Optional.empty());
    }

    /**
     * 조회 - category 전체 (페이징 x)
     */
    @Test
    @DisplayName("CategoryService.getAllCategory() 테스트 (전체 조회)")
    public void getAllCategory() throws Exception {
        //given
        Category category1 = Category.builder().name("개발").build();
        Category category2 = Category.builder().name("기획").build();
        Category category3 = Category.builder().name("영업").build();
        
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        //when
        List<CategoryResponseDto> allCategory = categoryService.getAllCategory();

        //then
        assertThat(allCategory.size()).isEqualTo(3);
        assertThat(allCategory.get(0).getId()).isEqualTo(category1.getId());
        assertThat(allCategory.get(0).getName()).isEqualTo(category1.getName());
    }
}
