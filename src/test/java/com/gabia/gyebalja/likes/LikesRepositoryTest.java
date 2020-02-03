package com.gabia.gyebalja.likes;

import com.gabia.gyebalja.domain.*;
import com.gabia.gyebalja.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class LikesRepositoryTest {
    @PersistenceContext
    EntityManager em;

    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EducationRepository educationRepository;

    private Likes likes;
    private Board board;
    private Department department;
    private User user;
    private Education education;
    private Category category;

    @Autowired
    public LikesRepositoryTest(LikesRepository likesRepository, BoardRepository boardRepository ,DepartmentRepository departmentRepository, UserRepository userRepository, CategoryRepository categoryRepository, EducationRepository educationRepository){
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
                .gender(UserGender.MALE)
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

        // Likes
        this.likes = Likes.builder().board(board).user(user).build();
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

        Long totalNumberOfData = likesRepository.count();
        Likes likes = this.likes;

        // when
        Likes saveLikes = likesRepository.save(likes);
        em.flush();
        em.clear();

        // then
        Likes findLikes = likesRepository.findById(saveLikes.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(likesRepository.count()).isEqualTo(totalNumberOfData + 1);
        assertThat(findLikes.getId()).isEqualTo(saveLikes.getId());
        assertThat(findLikes.getUser().getId()).isEqualTo(findLikes.getUser().getId());
        assertThat(findLikes.getBoard().getId()).isEqualTo(findLikes.getBoard().getId());
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

        Likes likes = this.likes;
        Likes saveLikes = likesRepository.save(likes);
        em.flush();
        em.clear();

        // when
        Likes findLikes = likesRepository.findById(saveLikes.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        // then
        assertThat(findLikes.getId()).isEqualTo(saveLikes.getId());
        assertThat(findLikes.getUser().getId()).isEqualTo(findLikes.getUser().getId());
        assertThat(findLikes.getBoard().getId()).isEqualTo(findLikes.getBoard().getId());
    }

    /** Update Test */
    @Test
    public void updateTest(){
        /**
         * [의문]
         * likes의 user, board가 수정될 경우 없음
         * 테스트를 위해 changeUser, changeBoard 등의 메소드를 생성하여 억지로 테스트할 수 있으나
         * 이러한 메소드를 만드는 것 자체가 맞는지 고민되어 우선 생략함
         * */
        // given

        // when

        // then
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

        Long totalNumberOfData = likesRepository.count();
        Likes likes = this.likes;
        Likes saveLikes = likesRepository.save(likes);
        em.flush();
        em.clear();

        // when
        likesRepository.deleteById(saveLikes.getId());

        // then
        assertThat(likesRepository.count()).isEqualTo(totalNumberOfData);
        assertThat(likesRepository.findById(saveLikes.getId())).isEqualTo(Optional.empty());    // 빈 객체인지 확인
    }
}