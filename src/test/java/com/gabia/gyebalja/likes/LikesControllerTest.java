package com.gabia.gyebalja.likes;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.likes.LikesRequestDto;
import com.gabia.gyebalja.dto.likes.LikesResponseDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.LikesRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.LikesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LikesControllerTest {

    @Autowired private BoardRepository boardRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private EducationRepository educationRepository;

    @Autowired
    private LikesService likesService;

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Board board;
    private Department department;
    private User user;
    private Education education;
    private Category category;

    @BeforeEach
    public void setUp(){
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);
    }

    @AfterEach
    public void cleanUp() {
        this.boardRepository.deleteAll();
        this.departmentRepository.deleteAll();
        this.userRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.educationRepository.deleteAll();
    }

    @Autowired
    public LikesControllerTest() {
        // Department
        this.department = Department.builder()
                .name("테스트팀")
                .depth(0)
                .parentDepartment(null)
                .build();

        // User
        this.user = User.builder()
                .email("gabiaUser@gabia.com")
                .password("1234")
                .name("가비아")
                .gender(GenderType.MALE)
                .phone("010-2345-5678")
                .tel("02-2345-5678")
                .positionId(5L)
                .positionName("직원")
                .department(this.department)
                .profileImg(null)
                .build();

        // Category
        this.category = Category.builder()
                .name("개발")
                .build();

        // Education
        this.education = Education.builder()
                .title("테스트 - Mysql 초급 강좌 제목")
                .content("테스트 - Mysql 초급 강좌 본문")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .totalHours(10)
                .type(EducationType.ONLINE)
                .place("테스트 - 인프런 온라인 교육 사이트")
                .user(this.user)
                .category(this.category)
                .build();

        // Board
        this.board = Board.builder().
                title("테스트 - 게시글 제목")
                .content("테스트 - 게시글 본문")
                .views(0)
                .user(this.user)
                .education(this.education)
                .build();
    }

    /** 등록 - likes 한 개 */
    @Test
    @DisplayName("LikesController.postOneLikes() 테스트 (한 개)")
    public void postOneLikes(){
        // given
        String url = "http://localhost:" + port + "/api/v1/likes";
        LikesRequestDto likesRequestDto = LikesRequestDto.builder().userId(user.getId()).boardId(board.getId()).build();

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.postForEntity(url, likesRequestDto, CommonJsonFormat.class);

        // then
        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
//        assertThat(response.get("id")).isNotNull();
//        assertThat(response.get("userId").toString()).isEqualTo(user.getId().toString());
//        assertThat(response.get("boardId").toString()).isEqualTo(board.getId().toString());
    }

    /** 삭제 - likes 한 개 */
    @Test
    @DisplayName("LikesController.deleteOneLikes() 테스트 (한 개)")
    public void deleteOneLikes(){
        // given
        LikesRequestDto likesRequestDto = LikesRequestDto.builder().userId(user.getId()).boardId(board.getId()).build();
        LikesResponseDto likesResponseDto = likesService.save(likesRequestDto);
        String url = "http://localhost:" + port + "/api/v1/likes/users/" + user.getId() + "/boards/" + board.getId();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(headers);

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, CommonJsonFormat.class);

        // then
        ArrayList response = (ArrayList) responseEntity.getBody().getResponse();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.NO_CONTENT.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.NO_CONTENT.getMessage());
    }
}
