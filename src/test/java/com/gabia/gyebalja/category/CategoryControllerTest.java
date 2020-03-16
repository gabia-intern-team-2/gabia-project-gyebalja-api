package com.gabia.gyebalja.category;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.dto.category.CategoryRequestDto;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * Author : 정태균
 * Part : All
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.yml")
public class CategoryControllerTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    public CategoryControllerTest() {
        // Interceptor 해제
        System.setProperty("spring.profiles.active.test", "true");
    }

    @AfterEach
    public void cleanUp() {
        this.categoryRepository.deleteAll();
    }

    /**
     * 등록 - category 한 건 (단건 등록)
     */
    @Test
    @DisplayName("CategoryApiController.postOneCateogry() 테스트 (단건 등록)")
    public void postOneCategory() throws Exception {
        //given
        String url = "http://localhost:" + port + "/api/v1/categories";

        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                                                                    .name("개발")
                                                                    .build();

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.postForEntity(url, categoryRequestDto, CommonJsonFormat.class);
        CategoryResponseDto findCategoryDto = categoryService.getOneCategory(Long.parseLong(responseEntity.getBody().getResponse().toString()));

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(responseEntity.getBody().getResponse().toString()).isEqualTo(findCategoryDto.getId().toString());
    }

    /**
     * 조회 - category 한 건 (단건조회)
     */
    @Test
    @DisplayName("CategoryApiController.getOneCateogry() 테스트 (단건 조회)")
    public void getOneCategory() throws Exception {
        //given
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                                                .name("개발")
                                                .build();
        Long savedId = categoryService.postOneCategory(categoryRequestDto);
        String url = "http://localhost:" + port + "/api/v1/categories/" + savedId;

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);
        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(response.get("id").toString()).isEqualTo(savedId.toString());
        assertThat(response.get("name")).isEqualTo(categoryRequestDto.getName());
    }

    /**
     * 수정 - category 한 건 (단건수정)
     */
    @Test
    @DisplayName("CategoryApiController.putOneCateogry() 테스트 (단건 수정)")
    public void putOneCategory() throws Exception {
        //given
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                                                                .name("개발")
                                                                .build();
        Long savedId = categoryService.postOneCategory(categoryRequestDto);
        String url = "http://localhost:" + port + "/api/v1/categories/" + savedId;
        String updateName = "기획";

        //when
        CategoryRequestDto updateRequestDto = CategoryRequestDto.builder()
                                                                .name(updateName)
                                                                .build();
        HttpEntity<CategoryRequestDto> requestEntity = new HttpEntity<>(updateRequestDto);
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, CommonJsonFormat.class);
        Category findCategory = categoryRepository.findById(savedId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        //then
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(responseEntity.getBody().getResponse().toString()).isEqualTo(savedId.toString());
        assertThat(findCategory.getName()).isEqualTo(updateName);
    }

    /**
     * 삭제 - category 한 건 (단건삭제)
     */
    @Test
    @DisplayName("CategoryApiController.deleteOneCateogry() 테스트 (단건 삭제)")
    public void deleteOneCategory() throws Exception {
        //given
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                                                                .name("개발")
                                                                .build();
        Long savedId = categoryService.postOneCategory(categoryRequestDto);
        long beforeDeleteCnt = categoryRepository.count();

        String url = "http://localhost:" + port + "/api/v1/categories/" + savedId;

        //when
        restTemplate.delete(url);

        //then
        assertThat(categoryRepository.findById(savedId)).isEqualTo(Optional.empty());
        assertThat(categoryRepository.count()).isEqualTo(beforeDeleteCnt-1);
    }

    /**
     * 조회 - category 전체 (페이징 x)
     */
    @Test
    @DisplayName("CategoryApiController.getAllCateogry() 테스트 (전체 조회)")
    public void getAllCategory() throws Exception {
        //given
        int totalNum = 30;
        String url = "http://localhost:" + port + "/api/v1/categories";
        for(int i =0; i<totalNum; i++) {
            Category category = Category.builder()
                                        .name("개발자" + i)
                                        .build();
            categoryRepository.save(category);
        }

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);
        ArrayList response = (ArrayList) responseEntity.getBody().getResponse();

        //then
        assertThat(response.size()).isEqualTo(totalNum);
    }
}
