package com.gabia.gyebalja.board;

import com.gabia.gyebalja.domain.*;
import com.gabia.gyebalja.dto.board.BoardRequestDto;
import com.gabia.gyebalja.dto.board.BoardResponseDto;
import com.gabia.gyebalja.repository.*;
import com.gabia.gyebalja.service.BoardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardControllerTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @PersistenceContext
    EntityManager em;

    private BoardRepository boardRepository;
    private DepartmentRepository departmentRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private EducationRepository educationRepository;

    private Department department;
    private User user;
    private Education education;
    private Category category;

    @AfterEach
    public void cleanUp(){
        System.out.println("-------------------------------");
        System.out.println("cleanUp()");
        this.boardRepository.deleteAll();
        this.departmentRepository.deleteAll();
        this.userRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.educationRepository.deleteAll();
    }

    @Autowired
    public BoardControllerTest(BoardRepository boardRepository, DepartmentRepository departmentRepository, UserRepository userRepository, CategoryRepository categoryRepository, EducationRepository educationRepository) {
        System.out.println("-------------------------------");
        System.out.println("BoardContollerTest()");
        // Repository
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
    }

    /** 등록 - board 한 건 (게시글 등록) */
    @Test
    public void postOneBoard(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);

        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        String url = "http://localhost:" + port + "/api/v1/boards";

        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, boardRequestDto, Long.class);

        // then
        BoardResponseDto boardResponseDto = boardService.findById(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        assertThat(boardResponseDto.getTitle()).isEqualTo(title);
        assertThat(boardResponseDto.getContent()).isEqualTo(content);
    }

    /** 조회 - board 한 건 (상세페이지) */
    @Test
    public void getOneBoard(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);

        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();

        Long saveId = boardService.save(boardRequestDto);
        System.out.println(saveId);

        String url = "http://localhost:" + port + "/api/v1/boards/" + saveId;

        // when
        ResponseEntity<BoardResponseDto> responseEntity = restTemplate.getForEntity(url, BoardResponseDto.class);
        System.out.println(responseEntity.getBody());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(title);
        assertThat(responseEntity.getBody().getContent()).isEqualTo(content);
//        assertThat(responseEntity.getBody().getCommentList().size()).isEqualTo(commentRepository.findByBoardId(targetId).size());    // 게시글의 댓글 테스트 (개수로 테스트)
    }

    /** 수정 - board 한 건 (상세페이지에서) */
    @Test
    public void putOneBoard(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);

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
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class); // restTemplate.put(url, boardDto)

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        Board board = boardRepository.findById(updateId).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));
        assertThat(board.getTitle()).isEqualTo(updateTitle);
        assertThat(board.getContent()).isEqualTo(updateContent);
    }

    /** 삭제 - board 한 건 (상세페이지에서) */
    @Test
    public void deleteOneBoard(){
        //given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);

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
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        assertThat(boardRepository.count()).isEqualTo(totalNumberOfData);
    }
}
