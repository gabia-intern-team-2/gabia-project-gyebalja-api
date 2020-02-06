package com.gabia.gyebalja.comment;

import com.gabia.gyebalja.domain.*;
import com.gabia.gyebalja.repository.*;
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
public class CommentRepositoryTest {
    @PersistenceContext
    EntityManager em;

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CategoryRepository categoryRepository;
    private final EducationRepository educationRepository;

    private Comment comment;
    private Board board;
    private User user;
    private Department department;
    private Category category;
    private Education education;

    @Autowired
    public CommentRepositoryTest(CommentRepository commentRepository, BoardRepository boardRepository, UserRepository userRepository, DepartmentRepository departmentRepository,
                                 CategoryRepository categoryRepository, EducationRepository educationRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
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
        this.board = Board.builder().title("테스트 - 게시글 제목").content("테스트 - 게시글 본문").views(0).user(this.user).education(this.education).build();

        // Comment
        this.comment = comment.builder().content("테스트 - 댓글 본문").board(this.board).user(this.user).build();
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

        Long totalNumberOfData = commentRepository.count();
        Comment comment = this.comment;
        em.flush();
        em.clear();

        // when
        Comment saveComment = commentRepository.save(comment);

        // then
        Comment findComment = commentRepository.findById(saveComment.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(commentRepository.count()).isEqualTo(totalNumberOfData + 1);
        assertThat(findComment.getId()).isEqualTo(saveComment.getId());
        assertThat(findComment.getContent()).isEqualTo(saveComment.getContent());
        assertThat(findComment.getBoard().getId()).isEqualTo(saveComment.getBoard().getId());
    }

    /** Retrieve Test */
    @Test
    public void findComment(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);

        Comment comment = this.comment;
        Comment saveComment = commentRepository.save(comment);
        em.flush();
        em.clear();

        // when
        Comment findComment = commentRepository.findById(saveComment.getId()).orElseThrow(()->new IllegalArgumentException("해당 데이터가 없습니다."));

        // then
        assertThat(findComment.getId()).isEqualTo(saveComment.getId());
        assertThat(findComment.getContent()).isEqualTo(saveComment.getContent());
        assertThat(findComment.getBoard().getId()).isEqualTo(saveComment.getBoard().getId());
        assertThat(findComment.getUser().getId()).isEqualTo(saveComment.getUser().getId());
    }

    /** Update Test */
    @Test
    public void updateComment(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);

        Comment comment = this.comment;
        Comment saveComment = commentRepository.save(comment);
        Comment findComment = commentRepository.findById(saveComment.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        String updateContent = "테스트 - 댓글 본문 업데이트";
        findComment.changeContent(updateContent);

        // when
        em.flush();
        em.clear();

        // then
        Comment updateComment = commentRepository.findById(findComment.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(updateComment.getContent()).isEqualTo(findComment.getContent());
    }

    /** Delete Test */
    @Test
    public void deleteComment(){
        // given
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);

        Long totalNumberOfData = commentRepository.count();
        Comment comment = this.comment;
        Comment saveComment = commentRepository.save(comment);
        em.flush();
        em.clear();

        // when
        commentRepository.deleteById(saveComment.getId());

        // then
        assertThat(commentRepository.count()).isEqualTo(totalNumberOfData);
        assertThat(commentRepository.findById(saveComment.getId())).isEqualTo(Optional.empty());
    }
}