package com.gabia.gyebalja.comment;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.comment.CommentRequestDto;
import com.gabia.gyebalja.dto.comment.CommentResponseDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.CommentRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.CommentService;
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
public class CommentServiceTest {

    @Autowired private CommentRepository commentRepository;
    @Autowired private BoardRepository boardRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private EducationRepository educationRepository;

    @Autowired
    private CommentService commentService;

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
        boardRepository.save(this.board);
    }

    @Autowired
    public CommentServiceTest(){
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

    @Test
    @DisplayName("commentService.postOneComment() 테스트 (단건 저장)")
    public void postOneCommentTest(){
        // given
        String content = "테스트 - 댓글 본문";
        CommentRequestDto commentRequestDto = CommentRequestDto.builder().content(content).userId(user.getId()).boardId(board.getId()).build();

        // when
        Long saveId = commentService.postOneComment(commentRequestDto);
        em.flush();
        em.clear();

        // then
        CommentResponseDto commentResponseDto = commentService.getOneComment(saveId);
        assertThat(commentResponseDto.getId()).isEqualTo(saveId);
    }

    @Test
    @DisplayName("commentService.getOneComment() 테스트 (단건 조회)")
    public void getOneCommentTest(){
        // given
        String content = "테스트 - 댓글 본문";
        CommentRequestDto commentRequestDto = CommentRequestDto.builder().content(content).userId(user.getId()).boardId(board.getId()).build();

        Long saveId = commentService.postOneComment(commentRequestDto);
        em.flush();
        em.clear();

        // when
        CommentResponseDto commentResponseDto = commentService.getOneComment(saveId);

        // then
        assertThat(commentResponseDto.getId()).isEqualTo(saveId);
        assertThat(commentResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("commentService.putOneComment() 테스트 (단건 업데이트)")
    public void putOneCommentTest(){
        // given
        String content = "테스트 - 댓글 본문";
        CommentRequestDto commentRequestDto = CommentRequestDto.builder().content(content).userId(user.getId()).boardId(board.getId()).build();
        String updateContent = "테스트 - 댓글 본문 업데이트";
        CommentRequestDto updateCommentRequestDto = CommentRequestDto.builder().content(updateContent).userId(user.getId()).boardId(board.getId()).build();

        Long saveId = commentService.postOneComment(commentRequestDto);
        em.flush();
        em.clear();

        // then
        Long updateId = commentService.putOneComment(saveId, updateCommentRequestDto);

        // when
        CommentResponseDto commentResponseDto = commentService.getOneComment(updateId);
        assertThat(updateId).isEqualTo(saveId);
        assertThat(commentResponseDto.getContent()).isEqualTo(updateContent);
        System.out.println(commentResponseDto.toString());
    }

    @Test
    @DisplayName("commentService.deleteOneComment() 테스트 (단건 삭제)")
    public void deleteOneCommentTest(){
        // given
        String content = "테스트 - 댓글 본문";
        CommentRequestDto commentRequestDto = CommentRequestDto.builder().content(content).userId(user.getId()).boardId(board.getId()).build();

        Long saveId = commentService.postOneComment(commentRequestDto);

        // when
        Long deleteId = commentService.deleteOneComment(saveId);

        // then
        assertThat(deleteId).isEqualTo(saveId);
        assertThat(commentRepository.findById(deleteId)).isEqualTo(Optional.empty());
    }
}
