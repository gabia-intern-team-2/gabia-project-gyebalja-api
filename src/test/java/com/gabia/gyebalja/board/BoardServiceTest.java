package com.gabia.gyebalja.board;

import com.gabia.gyebalja.domain.*;
import com.gabia.gyebalja.dto.board.BoardRequestDto;
import com.gabia.gyebalja.dto.board.BoardResponseDto;
import com.gabia.gyebalja.repository.*;
import com.gabia.gyebalja.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @PersistenceContext
    EntityManager em;

    private final BoardRepository boardRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EducationRepository educationRepository;

    private Department department;
    private User user;
    private Education education;
    private Category category;

    @Autowired
    public BoardServiceTest(BoardRepository boardRepository, DepartmentRepository departmentRepository, UserRepository userRepository, CategoryRepository categoryRepository, EducationRepository educationRepository) {
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

    @Test
    @DisplayName("BoardService.save() 테스트 (단건 저장)")
    public void saveTest(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);

        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();

        // when
        Long saveId = boardService.save(boardRequestDto);
        em.clear();
        em.flush();

        // then
        Board responseBoard = boardRepository.findById(saveId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(responseBoard.getId()).isEqualTo(saveId);
        assertThat(responseBoard.getTitle()).isEqualTo(title);
        assertThat(responseBoard.getContent()).isEqualTo(content);
//        BoardResponseDto boardResponseDto = boardService.findById(saveId);
//        assertThat(boardResponseDto.getId()).isEqualTo(saveId);
    }

    @Test
    @DisplayName("BoardService.findById() 테스트 (단건 조회)")
    public void findTest(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);

        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto contetn";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();

        Long saveId = boardService.save(boardRequestDto);
        em.clear();
        em.flush();

        // when
        BoardResponseDto boardResponseDto = boardService.findById(saveId);

        // then
        Board responseBoard = boardRepository.findById(saveId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(responseBoard.getId()).isEqualTo(saveId);
        assertThat(responseBoard.getTitle()).isEqualTo(title);
        assertThat(responseBoard.getContent()).isEqualTo(content);
//        assertThat(boardResponseDto.getId()).isEqualTo(saveId);
//        assertThat(boardResponseDto.getTitle()).isEqualTo(title);
//        assertThat(boardResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("BoardService.update() 테스트 (단건 업데이트)")
    public void updateTest(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);

        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();
        String updateTitle = "테스트 - BoardRequestDto title 업데이트";
        String updateContent = "테스트 - BoardRequestDto content 업데이트";
        BoardRequestDto updateBoardRequestDto = BoardRequestDto.builder().title(updateTitle).content(updateContent).user(user).education(education).build();

        Long saveId = boardService.save(boardRequestDto);
        em.flush();
        em.clear();

        // when
        Long updateId = boardService.update(saveId, updateBoardRequestDto);

        // then
        assertThat(updateId).isEqualTo(saveId);
        Board responseBoard = boardRepository.findById(updateId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(responseBoard.getId()).isEqualTo(updateId);
        assertThat(responseBoard.getTitle()).isEqualTo(updateTitle);
        assertThat(responseBoard.getContent()).isEqualTo(updateContent);
//        BoardResponseDto boardResponseDto = boardService.findById(updateId);
//        assertThat(updateId).isEqualTo(saveId);
//        assertThat(boardResponseDto.getTitle()).isEqualTo(updateTitle);
//        assertThat(boardResponseDto.getContent()).isEqualTo(updateContent);
    }

    @Test
    @DisplayName("BoardService.delete() 테스트 (단건 삭제)")
    public void deleteTest(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);

        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).user(user).education(education).build();

        Long saveId = boardService.save(boardRequestDto);

        // when
        Long deleteId = boardService.delete(saveId);

        // then
        assertThat(deleteId).isEqualTo(saveId);
        assertThat(boardRepository.findById(deleteId)).isEqualTo(Optional.empty());
    }
}
