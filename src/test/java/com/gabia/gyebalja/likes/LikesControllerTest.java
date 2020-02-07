package com.gabia.gyebalja.likes;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.Likes;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.likes.LikesRequestDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.CommentRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LikesControllerTest {

    @Autowired
    private LikesService likesService;

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EducationRepository educationRepository;

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
        System.out.println(">>>>>>>>>>>>>>>>>>>> cleanUp() method");

        this.boardRepository.deleteAll();
        this.departmentRepository.deleteAll();
        this.userRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.educationRepository.deleteAll();
    }
    @Autowired
    public LikesControllerTest(LikesRepository likesRepository, BoardRepository boardRepository, DepartmentRepository departmentRepository, UserRepository userRepository, CategoryRepository categoryRepository, EducationRepository educationRepository, CommentRepository commentRepository) {
        System.out.println(">>>>>>>>>>>>>>>>>>>> LikesControllerTest() method");

        // Repository
        this.likesRepository = likesRepository;
        this.boardRepository = boardRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.educationRepository = educationRepository;

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
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, likesRequestDto, Long.class);

        // then
        Likes likes = likesRepository.findById(responseEntity.getBody()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(likes.getId()).isEqualTo(responseEntity.getBody());
    }

    /** 삭제 - likes 한 개 */
    @Test
    @DisplayName("LikesController.deleteOneLikes() 테스트 (한 개)")
    public void deleteOneLikes(){
        // given
        LikesRequestDto likesRequestDto = LikesRequestDto.builder().userId(user.getId()).boardId(board.getId()).build();
        Long saveId = likesService.save(likesRequestDto);
        String url = "http://localhost:" + port + "/api/v1/likes/users/" + user.getId() + "/boards/" + board.getId();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(headers);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(likesRepository.findById(saveId)).isEqualTo(Optional.empty());
    }
}
