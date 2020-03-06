package com.gabia.gyebalja.boardImg;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.BoardImg;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.repository.BoardImgRepository;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
public class BoardImgRepositoryTest {
    @PersistenceContext
    EntityManager em;

    private final BoardImgRepository boardImgRepository;
    private final BoardRepository boardRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EducationRepository educationRepository;

    private BoardImg boardImg;
    private Board board;
    private Department department;
    private User user;
    private Education education;
    private Category category;

    @Autowired
    public BoardImgRepositoryTest(BoardImgRepository boardImgRepository, BoardRepository boardRepository, DepartmentRepository departmentRepository, UserRepository userRepository, CategoryRepository categoryRepository, EducationRepository educationRepository){
        // Repository
        this.boardImgRepository = boardImgRepository;
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

        // BoardImg
        this.boardImg = BoardImg.builder().board(this.board).imgPath("테스트 - 이미지 경로").build();
    }

    /** Create Test */
    @Test
    public void saveTest(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);

        Long totalNumberOfData = boardImgRepository.count();
        BoardImg boardImg = this.boardImg;

        // when
        BoardImg saveBoardImg = boardImgRepository.save(this.boardImg);
        em.flush();
        em.clear();

        // then
        BoardImg findBoardImg = boardImgRepository.findById(saveBoardImg.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(boardImgRepository.count()).isEqualTo(totalNumberOfData + 1);
        assertThat(findBoardImg.getId()).isEqualTo(saveBoardImg.getId());
        assertThat(findBoardImg.getBoard().getId()).isEqualTo(saveBoardImg.getBoard().getId());
        assertThat(findBoardImg.getImgPath()).isEqualTo(saveBoardImg.getImgPath());
    }

    /** Retrieve Test */
    @Test
    public void findTest(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);

        BoardImg boardImg = this.boardImg;
        BoardImg saveBoardImg = boardImgRepository.save(boardImg);
        em.flush();
        em.clear();

        // when
        BoardImg findBoardImg = boardImgRepository.findById(saveBoardImg.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        // then
        assertThat(findBoardImg.getId()).isEqualTo(saveBoardImg.getId());
        assertThat(findBoardImg.getBoard().getId()).isEqualTo(saveBoardImg.getBoard().getId());
        assertThat(findBoardImg.getImgPath()).isEqualTo(saveBoardImg.getImgPath());
    }

    /** Update Test */
    @Test
    public void updateTest(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);

        BoardImg boardImg = this.boardImg;
        BoardImg saveBoardImg = boardImgRepository.save(boardImg);
        BoardImg findBoardImg = boardImgRepository.findById(saveBoardImg.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        String updateImgPath = "테스트 - 이미지 경로 업데이트";
        findBoardImg.changeImgPath(updateImgPath);

        // when
        em.flush();
        em.clear();

        // then
        BoardImg updateBoardImg = boardImgRepository.findById(findBoardImg.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(updateBoardImg.getId()).isEqualTo(findBoardImg.getId());
        assertThat(updateBoardImg.getBoard().getId()).isEqualTo(findBoardImg.getBoard().getId());
        assertThat(updateBoardImg.getImgPath()).isEqualTo(updateImgPath);
    }

    /** Delete Test */
    @Test
    public void deleteTest(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);

        Long totalNumberOfData = boardImgRepository.count();
        BoardImg boardImg = this.boardImg;
        BoardImg saveBoardImg = boardImgRepository.save(boardImg);
        em.flush();
        em.clear();

        // when
        boardImgRepository.deleteById(saveBoardImg.getId());

        // then
        assertThat(boardImgRepository.count()).isEqualTo(totalNumberOfData);
        assertThat(boardImgRepository.findById(saveBoardImg.getId())).isEqualTo(Optional.empty());
    }
}
