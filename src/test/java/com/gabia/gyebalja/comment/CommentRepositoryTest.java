package com.gabia.gyebalja.comment;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Comment;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.CommentRepository;
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
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired private CommentRepository commentRepository;
    @Autowired private BoardRepository boardRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private EducationRepository educationRepository;

    @PersistenceContext
    EntityManager em;

    private Comment comment;
    private Board board;
    private User user;
    private Department department;
    private Category category;
    private Education education;

    @BeforeEach
    public void setUp(){
        departmentRepository.save(this.department);
        userRepository.save(this.user);
        categoryRepository.save(this.category);
        educationRepository.save(this.education);
        boardRepository.save(this.board);
    }

    @Autowired
    public CommentRepositoryTest() {
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

        // Comment
        this.comment = comment.builder().content("테스트 - 댓글 본문").board(this.board).user(this.user).build();
    }


    /** Create Test */
    @Test
    public void saveTest(){
        // given
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