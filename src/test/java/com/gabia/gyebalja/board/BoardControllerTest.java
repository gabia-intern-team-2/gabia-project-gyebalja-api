package com.gabia.gyebalja.board;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Comment;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.board.BoardRequestDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.CommentRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.BoardService;
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
public class BoardControllerTest {

    @Autowired private BoardRepository boardRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private EducationRepository educationRepository;
    @Autowired private CommentRepository commentRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

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
    }

    @AfterEach
    public void cleanUp() {
        this.boardRepository.deleteAll();
        this.departmentRepository.deleteAll();
        this.userRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.educationRepository.deleteAll();
        this.commentRepository.deleteAll();
    }

    @Autowired
    public BoardControllerTest() {
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
    }

    /** 등록 - board 한 건 (게시글 등록) */
    @Test
    @DisplayName("BoardController.postOneBoard() 테스트 (단건 저장)")
    public void postOneBoard() {
        // given
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        String url = "http://localhost:" + port + "/api/v1/boards";

        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.postForEntity(url, boardRequestDto, CommonJsonFormat.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
//        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();
//        assertThat(response.get("id")).isNotNull();
//        assertThat(response.get("title")).isEqualTo(title);
//        assertThat(response.get("content")).isEqualTo(content);
    }

    /** 조회 - board 한 건 (상세페이지) */
    @Test
    @DisplayName("BoardController.getOneBoard() 테스트 (단건 조회)")
    public void getOneBoard() {
        // given
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();

        Long saveId = boardService.save(boardRequestDto);
        String url = "http://localhost:" + port + "/api/v1/boards/" + saveId;

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);

        // then
        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(response.get("id")).isNotNull();
        assertThat(response.get("title")).isEqualTo(title);
        assertThat(response.get("content")).isEqualTo(content);
    }

    @Test
    @DisplayName("BoardController.getOneBoard() 테스트 (단건 조회) - 댓글 테스트")
    public void getOneBoardWithComments() {
        // given
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();

        Long saveId = boardService.save(boardRequestDto);
        String url = "http://localhost:" + port + "/api/v1/boards/" + saveId;

        int totalNumberOfData = 29;
        Board board = boardRepository.findById(saveId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        for(int i =0; i < totalNumberOfData; i++) {
            commentRepository.save(Comment.builder().content("테스트 - 댓글").user(user).board(board).build());
        }

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);

        // then
        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(response.get("id")).isNotNull();
        assertThat(response.get("title")).isEqualTo(title);
        assertThat(response.get("content")).isEqualTo(content);
        assertThat(((ArrayList) response.get("commentList")).size()).isEqualTo(totalNumberOfData);
    }

    /**
     * 수정 - board 한 건 (상세페이지에서)
     */
    @Test
    @DisplayName("BoardController.putOneBoard() 테스트 (단건 업데이트)")
    public void putOneBoard() {
        // given
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto saveBoardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();
        Long saveId = boardService.save(saveBoardRequestDto);

        Long updateId = saveId;
        String updateTitle = "테스트 - BoardRequestDto title 업데이트";
        String updateContent = "테스트 - BoardRequestDto content 업데이트";
        String url = "http://localhost:" + port + "/api/v1/boards/" + updateId;

        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(updateTitle).content(updateContent).user(user).education(education).build();
        HttpEntity<BoardRequestDto> requestEntity = new HttpEntity<>(boardRequestDto);

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, CommonJsonFormat.class); // restTemplate.put(url, boardDto)

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
//        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();
//        assertThat(response.get("id")).isNotNull();
//        assertThat(response.get("title")).isEqualTo(updateTitle);
//        assertThat(response.get("content")).isEqualTo(updateContent);
    }

    /**
     * 삭제 - board 한 건 (상세페이지에서)
     */
    @Test
    @DisplayName("BoardController.deleteOneBoard() 테스트 (단건 삭제)")
    public void deleteOneBoard() {
        //given
        long totalNumberOfData = boardRepository.count();
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto saveBoardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();
        Long saveId = boardService.save(saveBoardRequestDto);

        Long deleteId = saveId;
        String url = "http://localhost:" + port + "/api/v1/boards/" + deleteId;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(headers);

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, CommonJsonFormat.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.NO_CONTENT.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.NO_CONTENT.getMessage());
        assertThat(boardRepository.count()).isEqualTo(totalNumberOfData);
    }

    /** 조회 - board 전체 (페이징) */
    @Test
    @DisplayName("BoardController.getAllBoard() 테스트 (전체 조회, 페이징)")
    public void getAllBoard() {
        //given
        int totalNumberOfData = 29;
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        for (int i = 0; i < totalNumberOfData; i++) {
            boardService.save(BoardRequestDto.builder().title(title).content(content).user(user).education(education).build());
        }

        String url = "http://localhost:" + port + "/api/v1/boards";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(headers);

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, CommonJsonFormat.class);

        // then
        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();
        ArrayList responseContentList = (ArrayList) response.get("content");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(response.get("totalElements")).isEqualTo(totalNumberOfData);
        responseContentList.forEach(responseContent -> assertThat(((LinkedHashMap) responseContent).get("title")).isEqualTo(title));
        responseContentList.forEach(responseContent -> assertThat(((LinkedHashMap) responseContent).get("content")).isEqualTo(content));
        // 참고 : totalElements=29, totalPages=3, last=false, size=10, number=0, sort={sorted=true, unsorted=false, empty=false}, numberOfElements=10, first=true, empty=false
    }
}
