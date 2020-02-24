package com.gabia.gyebalja.tag;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.domain.Tag;
import com.gabia.gyebalja.dto.tag.TagRequestDto;
import com.gabia.gyebalja.dto.tag.TagResponseDto;
import com.gabia.gyebalja.repository.TagRepository;
import com.gabia.gyebalja.service.TagService;
import org.assertj.core.api.Assertions;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TagControllerTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @PersistenceContext
    EntityManager em;

    @AfterEach
    public void cleanUp() {
        System.out.println("============================ cleanUp()");
        this.tagRepository.deleteAll();
    }

    /**
     * 등록 - tag 한 건 (단건 등록)
     */
    @Test
    @DisplayName("TagApiController.postOneTag() 테스트 (단건 등록)")
    public void postOneTag() throws Exception {
        //given
        String url = "http://localhost:" + port + "/api/v1/tags";

        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Spring")
                .build();
        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.postForEntity(url, tagRequestDto, CommonJsonFormat.class);
        TagResponseDto findTagDto = tagService.getOneTag(Long.parseLong(responseEntity.getBody().getResponse().toString()));
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(responseEntity.getBody().getResponse().toString()).isEqualTo(findTagDto.getId().toString());
    }

    /**
     * 조회 - tag 한 건 (단건조회)
     */
    @Test
    @DisplayName("TagApiController.getOneTag() 테스트 (단건 조회)")
    public void getOneTag() throws Exception {
        //given
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Spring")
                .build();
        Long savedId = tagService.postOneTag(tagRequestDto);
        String url = "http://localhost:" + port + "/api/v1/tags/" + savedId;
        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);
        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(response.get("id").toString()).isEqualTo(savedId.toString());
        assertThat(response.get("name")).isEqualTo(tagRequestDto.getName());
    }

    /**
     * 수정 - tag 한 건 (단건수정)
     */
    @Test
    @DisplayName("TagApiController.putOneTag() 테스트 (단건 수정)")
    public void putOneTag() throws Exception {
        //given
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Spring")
                .build();
        Long savedId = tagService.postOneTag(tagRequestDto);
        String url = "http://localhost:" + port + "/api/v1/tags/" + savedId;
        String updateName = "HTML";
        //when
        TagRequestDto updateRequestDto = TagRequestDto.builder()
                .name(updateName)
                .build();
        HttpEntity<TagRequestDto> requestEntity = new HttpEntity<>(updateRequestDto);
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, CommonJsonFormat.class);
        Tag findTag = tagRepository.findById(savedId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        //then
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(responseEntity.getBody().getResponse().toString()).isEqualTo(savedId.toString());
        assertThat(findTag.getName()).isEqualTo(updateName);
    }

    /**
     * 삭제 - tag 한 건 (단건삭제)
     */
    @Test
    @DisplayName("TagApiController.deleteOneTag() 테스트 (단건 삭제)")
    public void deleteOneTag() throws Exception {
        //given
        TagRequestDto tagRequestDto = TagRequestDto.builder()
                .name("Spring")
                .build();
        Long savedId = tagService.postOneTag(tagRequestDto);
        long beforeDeleteCnt = tagRepository.count();

        String url = "http://localhost:" + port + "/api/v1/tags/" + savedId;
        //when
        restTemplate.delete(url);
        //then
        assertThat(tagRepository.findById(savedId)).isEqualTo(Optional.empty());
        assertThat(tagRepository.count()).isEqualTo(beforeDeleteCnt-1);
    }

    /**
     * 조회 - tag 전체 (페이징 x)
     */
    @Test
    @DisplayName("TagApiController.getAllTag() 테스트 (전체 조회)")
    public void getAllTag() throws Exception {
        //given
        int totalNum = 30;
        String url = "http://localhost:" + port + "/api/v1/tags";
        for(int i =0; i<totalNum; i++) {
            Tag tag = Tag.builder()
                    .name("Spring" + i)
                    .build();
            tagRepository.save(tag);
        }
        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);
        ArrayList response = (ArrayList) responseEntity.getBody().getResponse();
        //then
        assertThat(response.size()).isEqualTo(totalNum);
    }
}
