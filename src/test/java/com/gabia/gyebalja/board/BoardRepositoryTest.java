package com.gabia.gyebalja.board;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : 이현재
 * Part : All
 */

@Transactional
@DataJpaTest(properties = "spring.config.location=classpath:application-test.yml")
public class BoardRepositoryTest {

    @Autowired private BoardRepository boardRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private EducationRepository educationRepository;

    @PersistenceContext
    EntityManager em;

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
    }

    @Autowired
    public BoardRepositoryTest() {
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
        this.board = Board.builder().title("테스트 - 게시글 제목").content("테스트 - 게시글 본문").views(0).user(this.user).education(this.education).build();
    }

    /** Create Test */
    @Test
    public void saveTest() {
        // given
        Long totalNumberOfData = boardRepository.count();
        Board board = this.board;

        // when
        Board saveBoard = boardRepository.save(board);
        em.flush();
        em.clear();

        // then
        Board findBoard = boardRepository.findById(saveBoard.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(boardRepository.count()).isEqualTo(totalNumberOfData + 1);
        assertThat(findBoard.getId()).isEqualTo(saveBoard.getId());
        assertThat(findBoard.getTitle()).isEqualTo(saveBoard.getTitle());
        assertThat(findBoard.getContent()).isEqualTo(saveBoard.getContent());
    }

    /** Retrieve Test */
    @Test
    public void findTest() {
        // given
        Board board = this.board;
        Board saveBoard = boardRepository.save(board);
        em.flush();
        em.clear();

        // when
        Board findBoard = boardRepository.findById(saveBoard.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        // then
        assertThat(findBoard.getId()).isEqualTo(saveBoard.getId());
        assertThat(findBoard.getTitle()).isEqualTo(saveBoard.getTitle());
        assertThat(findBoard.getContent()).isEqualTo(saveBoard.getContent());
        assertThat(findBoard.getUser().getId()).isEqualTo(saveBoard.getUser().getId());
        assertThat(findBoard.getEducation().getId()).isEqualTo(saveBoard.getEducation().getId());
    }

    /** Update Test */
    @Test
    public void updateTest() {
        // given
        Board board = this.board;
        Board savedBoard = boardRepository.save(board);
        Board findBoard = boardRepository.findById(savedBoard.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        String updateTitle = "테스트 - 게시글 제목 업데이트";
        String updateContent = "테스트 - 게시글 본문 업데이트";
        findBoard.changeTitle(updateTitle);
        findBoard.changeContent(updateContent);

        // when
        em.flush();
        em.clear();

        // then
        Board updateBoard = boardRepository.findById(findBoard.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(updateBoard.getTitle()).isEqualTo(findBoard.getTitle());
        assertThat(updateBoard.getContent()).isEqualTo(findBoard.getContent());
    }

    /** Delete Test */
    @Test
    public void deleteTest() {
        // given
        Long totalNumberOfData = boardRepository.count();
        Board board = this.board;
        Board saveBoard = boardRepository.save(board);
        em.flush();
        em.clear();

        // when
        boardRepository.deleteById(saveBoard.getId());

        // then
        assertThat(boardRepository.count()).isEqualTo(totalNumberOfData);
        assertThat(boardRepository.findById(saveBoard.getId())).isEqualTo(Optional.empty());
    }
}


