package com.gabia.gyebalja.likes;

import com.gabia.gyebalja.domain.*;
import com.gabia.gyebalja.dto.likes.LikesRequestDto;
import com.gabia.gyebalja.dto.likes.LikesResponseDto;
import com.gabia.gyebalja.repository.*;
import com.gabia.gyebalja.service.LikesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class LikesServiceTest {

    @Autowired
    private LikesService likesService;

    @PersistenceContext
    EntityManager em;

    private final BoardRepository boardRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EducationRepository educationRepository;
    private final LikesRepository likesRepository;

    private Department department;
    private User user;
    private Education education;
    private Category category;
    private Board board;

    @Autowired
    public LikesServiceTest(BoardRepository boardRepository, DepartmentRepository departmentRepository, UserRepository userRepository, CategoryRepository categoryRepository, EducationRepository educationRepository, LikesRepository likesRepository){
        this.boardRepository = boardRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.educationRepository = educationRepository;
        this.likesRepository = likesRepository;

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

    @BeforeEach
    public void setUp(){
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);
    }

    @Test
    @DisplayName("LikesService.save() 테스트 (한 개)")
    public void saveTest(){
        // given
        LikesRequestDto likesRequestDto = LikesRequestDto.builder().userId(user.getId()).boardId(board.getId()).build();

        // when
        Long saveId = likesService.save(likesRequestDto);
        em.flush();
        em.clear();

        // then
        Likes likes = likesRepository.findById(saveId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        System.out.println(likes.getUser().toString());
        System.out.println(likes.getBoard().toString());
    }

    @Test
    @DisplayName("LikesService.delete() 테스트 (한 개)")
    public void deleteTest(){
        // given
        LikesRequestDto likesRequestDto = LikesRequestDto.builder().userId(user.getId()).boardId(board.getId()).build();
        Long saveId = likesService.save(likesRequestDto);

        // then
        Long deleteId = likesService.delete(user.getId(), board.getId());

        // when
        // 임시 값 200L
        assertThat(deleteId).isEqualTo(200L);
        assertThat(likesRepository.findById(deleteId)).isEqualTo(Optional.empty());
    }
}
