package com.gabia.gyebalja.comment;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.comment.CommentRequestDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.CommentRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.CommentService;
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
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : 이현재
 * Part : All
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.yml")
public class CommentControllerTest {

    @Autowired private BoardRepository boardRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private EducationRepository educationRepository;
    @Autowired private CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Department department;
    private User user;
    private Education education;
    private Category category;
    private Board board;

    @BeforeEach
    public void setUp(){
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);
    }

    @AfterEach
    public void cleanUp(){
        this.boardRepository.deleteAll();
        this.departmentRepository.deleteAll();
        this.userRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.educationRepository.deleteAll();
        this.commentRepository.deleteAll();
    }

    @Autowired
    public CommentControllerTest(){
        // Interceptor 해제
        System.setProperty("spring.profiles.active.test", "true");

        // Department
        this.department = Department.builder()
                .name("테스트팀")
                .depth(0)
                .parentDepartment(null)
                .build();

        // User
        this.user = User.builder()
                .email("gabiaUser@gabia.com")
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
        this.board = Board.builder()
                .title("테스트 - 게시글 제목")
                .content("테스트 - 게시글 본문")
                .views(0)
                .user(this.user)
                .education(this.education)
                .build();
    }

    /** 등록 - comment 한 건 */
    @Test
    @DisplayName("CommentController.postOneComment() 테스트 (단건 저장)")
    public void postOneComment(){
        //given
        String content = "테스트 - 댓글 작성";
        String url = "http://localhost:" + port + "/api/v1/comments";

        CommentRequestDto commentRequestDto = CommentRequestDto.builder().content(content).userId(user.getId()).boardId(board.getId()).build();

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.postForEntity(url, commentRequestDto, CommonJsonFormat.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
    }

    /** 조회 - comment 한 건 */
    @Test
    @DisplayName("CommentController.getOneComment() 테스트 (단건 조회)")
    public void getOneComment(){
        //given
        String content = "테스트 - 댓글 작성";
        CommentRequestDto commentRequestDto = CommentRequestDto.builder().content(content).userId(user.getId()).boardId(board.getId()).build();

        Long saveId = commentService.postOneComment(commentRequestDto);
        String url = "http://localhost:" + port + "/api/v1/comments/" + saveId;

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);

        //then
        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(response.get("id").toString()).isEqualTo(saveId.toString());
        assertThat(response.get("content")).isEqualTo(content);
        assertThat(response.get("userId").toString()).isEqualTo(user.getId().toString());
        assertThat(response.get("boardId").toString()).isEqualTo(board.getId().toString());
    }

    /** 수정 - comment 한 건 */
    @Test
    @DisplayName("CommentController.putOneComment() 테스트 (단건 업데이트)")
    public void putOneComment(){
        //given
        String content = "테스트 - 댓글 작성";
        CommentRequestDto saveCommentRequestDto = CommentRequestDto.builder().content(content).userId(user.getId()).boardId(board.getId()).build();
        Long saveId = commentService.postOneComment(saveCommentRequestDto);

        Long updateId = saveId;
        String updateContent = "테스트 - 댓글 작성 업데이트";
        CommentRequestDto updateCommentRequestDto = CommentRequestDto.builder().content(updateContent).userId(user.getId()).boardId(board.getId()).build();

        String url = "http://localhost:" + port + "/api/v1/comments/" + updateId;
        HttpEntity<CommentRequestDto> requestEntity = new HttpEntity<>(updateCommentRequestDto);

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, CommonJsonFormat.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
    }

    /** 삭제 - comment 한 건
     *  데이터 개수로 테스트 진행 (삽입 전 개수와 삽입, 삭제 후 개수 동일 체크)
     * */
    @Test
    @DisplayName("CommentController.deleteOneComment() 테스트 (단건 삭제)")
    public void deleteOneComment(){
        //given
        long totalNumberOfData = commentRepository.count();
        String content = "테스트 - 댓글 작성";
        CommentRequestDto saveCommentRequestDto = CommentRequestDto.builder().content(content).userId(user.getId()).boardId(board.getId()).build();
        Long saveId = commentService.postOneComment(saveCommentRequestDto);

        Long deleteId = saveId;
        String url = "http://localhost:" + port + "/api/v1/comments/" + deleteId;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(headers);

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, CommonJsonFormat.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(commentRepository.count()).isEqualTo(totalNumberOfData);
    }

    /** 조회 - comment 전체 */
    @Test
    @DisplayName("CommentController.getAllComment() 테스트 (전체 조회)")
    public void getAllComment(){
        // given
        int originalTotalNumberOfData = (int) commentRepository.count();
        int targetIndex = originalTotalNumberOfData;
        int totalNumberOfData = 29;
        String content = "테스트 - 댓글 본문";
        CommentRequestDto commentRequestDto = CommentRequestDto.builder().content(content).userId(user.getId()).boardId(board.getId()).build();

        for (int i = 0; i < totalNumberOfData; i++) {
            commentService.postOneComment(commentRequestDto);
        }

        String url = "http://localhost:" + port + "/api/v1/boards/" + board.getId() + "/comments";

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
    }
}
