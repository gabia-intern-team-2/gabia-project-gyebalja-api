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
import com.gabia.gyebalja.dto.likes.LikesResponseDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.LikesRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.LikesService;
import org.junit.jupiter.api.BeforeEach;
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
public class LikesServiceTest {

    @Autowired private BoardRepository boardRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private EducationRepository educationRepository;
    @Autowired private LikesRepository likesRepository;

    @Autowired
    private LikesService likesService;

    @PersistenceContext
    private EntityManager em;

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

    @Autowired
    public LikesServiceTest(){
        // Department
        this.department = Department.builder()
                .name("테스트 - 부서")
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

    @Test
    @DisplayName("likesService.postOneLikes() 테스트 (한 개)")
    public void postOneLikesTest(){
        // given
        LikesRequestDto likesRequestDto = LikesRequestDto.builder().userId(user.getId()).boardId(board.getId()).build();

        // when
        Long saveId = likesService.postOneLikes(likesRequestDto);
        em.flush();
        em.clear();

        // then
        Likes likes = likesRepository.findById(saveId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(likes.getUser().getId()).isEqualTo(likesRequestDto.getUserId());
        assertThat(likes.getBoard().getId()).isEqualTo(likesRequestDto.getBoardId());
    }

    @Test
    @DisplayName("likesService.deleteOneLikes() 테스트 (한 개)")
    public void deleteOneLikesTest(){
        // given
        LikesRequestDto likesRequestDto = LikesRequestDto.builder().userId(user.getId()).boardId(board.getId()).build();
        Long saveId = likesService.postOneLikes(likesRequestDto);

        // then
        Long deleteId = likesService.deleteOneLikes(user.getId(), board.getId());

        // when
        // 검토 - (임시) 200L
        assertThat(deleteId).isEqualTo(user.getId());
        assertThat(likesRepository.findById(deleteId)).isEqualTo(Optional.empty());
    }
}
