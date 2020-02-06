package com.gabia.gyebalja.category;

import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class CategoryRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("Category 저장 테스트(save)")
    public void saveTest() throws Exception {
        //given
        Long beforeCnt = categoryRepository.count();

        Category category = Category.builder()
                .name("개발자")
                .build();

        //when
        categoryRepository.save(category);
        em.clear();  //영속성 컨텍스트 초기화 ( 1차캐시에서 조회하는것을 막기위해)

        Category findCategory = categoryRepository.findById(category.getId()).get();

        //then
        assertThat(findCategory.getId()).isEqualTo(category.getId());
        assertThat(findCategory.getName()).isEqualTo(category.getName());
        assertThat(categoryRepository.count()).isEqualTo(beforeCnt+1);

    }

    @Test
    @DisplayName("Category 단건조회 테스트(findById)")
    public void findByIdTest() throws Exception {
        //given
        Category category1 = Category.builder()
                .name("개발자")
                .build();
        categoryRepository.save(category1);

        Category category2 = Category.builder()
                .name("기획자")
                .build();
        categoryRepository.save(category2);

        em.clear();
        //when
        Category findCategory = categoryRepository.findById(category2.getId()).get();

        //then
        assertThat(findCategory.getId()).isEqualTo(category2.getId());
        assertThat(findCategory.getName()).isEqualTo(category2.getName());
    }

    @Test
    @DisplayName("Category 전체 조회 테스트(findAll)")
    public void findAllTest() throws Exception {
        //given
        Category category1 = Category.builder()
                .name("개발자")
                .build();
        categoryRepository.save(category1);

        Category category2 = Category.builder()
                .name("기획자")
                .build();
        categoryRepository.save(category2);

        em.clear();
        //when
        List<Category> allCategory = categoryRepository.findAll();

        //then
        assertThat(allCategory.size()).isEqualTo(2);
        assertThat(allCategory.get(0).getId()).isEqualTo(category1.getId());
        assertThat(allCategory.get(0).getName()).isEqualTo(category1.getName());
    }

    @Test
    @DisplayName("Category 갯수 테스트(count)")
    public void countTest() throws Exception {
        //given
        Category category1 = Category.builder()
                .name("개발자")
                .build();
        categoryRepository.save(category1);

        Category category2 = Category.builder()
                .name("기획자")
                .build();
        categoryRepository.save(category2);
        //when
        long count = categoryRepository.count();
        //then
        assertThat(count).isEqualTo(2);

    }

    @Test
    @DisplayName("Category 삭제 테스트(delete")
    public void deleteTest() throws Exception {
        //given
        Category category1 = Category.builder()
                .name("개발자")
                .build();
        categoryRepository.save(category1);

        Category category2 = Category.builder()
                .name("기획자")
                .build();
        categoryRepository.save(category2);

        long beforeDeleteCnt = categoryRepository.count();
        //when
        categoryRepository.delete(category1);
        //then
        assertThat(categoryRepository.count()).isEqualTo(beforeDeleteCnt-1);
        assertThat(categoryRepository.findById(category1.getId())).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("Category 업데이트 테스트(update)")
    public void updateTest() throws Exception {
        //given
        String updateName = "이름 업데이트";
        
        Category category = Category.builder()
                                    .name("개발자")
                                    .build();
        categoryRepository.save(category);
        long beforeUpdateCnt = categoryRepository.count();
        //when
        category.changeCategoryName(updateName);
        em.flush();
        Category findCategory = categoryRepository.findById(category.getId()).get();
        
        //then
        assertThat(findCategory.getId()).isEqualTo(category.getId());
        assertThat(findCategory.getName()).isEqualTo(updateName);
        assertThat(categoryRepository.count()).isEqualTo(beforeUpdateCnt);

        
    }
        


}
