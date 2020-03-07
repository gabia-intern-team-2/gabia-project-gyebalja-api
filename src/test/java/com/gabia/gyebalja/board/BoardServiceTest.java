package com.gabia.gyebalja.board;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Comment;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.Likes;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.board.BoardAllResponseDto;
import com.gabia.gyebalja.dto.board.BoardRequestDto;
import com.gabia.gyebalja.dto.board.BoardDetailResponseDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.CommentRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.LikesRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class BoardServiceTest {

    @Autowired private BoardRepository boardRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private EducationRepository educationRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private LikesRepository likesRepository;

    @Autowired
    private BoardService boardService;

    @PersistenceContext
    EntityManager em;

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
    public BoardServiceTest() {
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
    }

    @Test
    @DisplayName("boardService.postOneBoard() 테스트 (단건 저장)")
    public void postOneBoardTest(){
        // given
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).userId(user.getId()).educationId(education.getId()).build();

        // when
        Long saveId = boardService.postOneBoard(boardRequestDto);
        em.clear();
        em.flush();

        // then
        BoardDetailResponseDto boardDetailResponseDto = boardService.getOneBoard(saveId);
        assertThat(boardDetailResponseDto.getId()).isEqualTo(saveId);
    }

    @Test
    @DisplayName("boardService.getOneBoard() 테스트 (단건 조회)")
    public void getOneBoardTest(){
        // given
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).userId(user.getId()).educationId(education.getId()).build();

        Long saveId = boardService.postOneBoard(boardRequestDto);
        em.clear();
        em.flush();

        // when
        BoardDetailResponseDto boardDetailResponseDto = boardService.getOneBoard(saveId);

        // then
        assertThat(boardDetailResponseDto.getId()).isEqualTo(saveId);
        assertThat(boardDetailResponseDto.getTitle()).isEqualTo(title);
        assertThat(boardDetailResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("boardService.getOneBoardTestWithCommentsAndLikes() 테스트 (단건 조회) - 댓글 테스트, 좋아요 개수 테스트")
    public void getOneBoardTestWithCommentsAndLikes(){
        // given
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).userId(user.getId()).educationId(education.getId()).build();

        Long saveId = boardService.postOneBoard(boardRequestDto);

        int totalNumberOfData = 29;
        Board board = boardRepository.findById(saveId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        for (int i = 0; i < totalNumberOfData; i++) {
            commentRepository.save(Comment.builder().content("테스트 - 댓글").user(user).board(board).build());
            likesRepository.save(Likes.builder().board(board).user(user).build());
        }
        em.flush();
        em.clear();

        // when
        BoardDetailResponseDto boardDetailResponseDto = boardService.getOneBoard(saveId);

        // then
        assertThat(boardDetailResponseDto.getId()).isEqualTo(saveId);
        assertThat(boardDetailResponseDto.getTitle()).isEqualTo(title);
        assertThat(boardDetailResponseDto.getContent()).isEqualTo(content);
        assertThat(boardDetailResponseDto.getCommentList().size()).isEqualTo(totalNumberOfData);
        assertThat(boardDetailResponseDto.getLikes()).isEqualTo(totalNumberOfData);
    }

    @Test
    @DisplayName("boardService.getOneBoardTestWithViews() 테스트 (단건 조회) - 조회수 테스트")
    public void getOneBoardTestWithViews(){
        // given
        int totalNumberOfData = 29;
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).userId(user.getId()).educationId(education.getId()).build();

        Long saveId = boardService.postOneBoard(boardRequestDto);
        em.clear();
        em.flush();

        // when
        BoardDetailResponseDto boardDetailResponseDto = boardService.getOneBoard(saveId);
        for (int i = 0; i < totalNumberOfData - 1; i++) {
            boardDetailResponseDto = boardService.getOneBoard(saveId);
        }

        // then
        assertThat(boardDetailResponseDto.getId()).isEqualTo(saveId);
        assertThat(boardDetailResponseDto.getTitle()).isEqualTo(title);
        assertThat(boardDetailResponseDto.getContent()).isEqualTo(content);
        assertThat(boardDetailResponseDto.getViews()).isEqualTo(totalNumberOfData);
    }

    @Test
    @DisplayName("boardService.putOneBoard() 테스트 (단건 업데이트)")
    public void putOneBoardTest() {
        // given
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).userId(user.getId()).educationId(education.getId()).build();
        String updateTitle = "테스트 - BoardRequestDto title 업데이트";
        String updateContent = "테스트 - BoardRequestDto content 업데이트";
        Education updateEducation = Education.builder()
                .title("테스트 - Mysql 초급 강좌 제목 업데이트")
                .content("테스트 - Mysql 초급 강좌 본문 업데이트")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .totalHours(10)
                .type(EducationType.ONLINE)
                .place("테스트 - 인프런 온라인 교육 사이트 업데이트")
                .user(this.user)
                .category(this.category)
                .build();
        educationRepository.save(updateEducation);
        BoardRequestDto updateBoardRequestDto = BoardRequestDto.builder().title(updateTitle).content(updateContent).userId(user.getId()).educationId(updateEducation.getId()).build();

        Long saveId = boardService.postOneBoard(boardRequestDto);
        em.flush();
        em.clear();

        // when
        Long updateId = boardService.putOneBoard(saveId, updateBoardRequestDto);

        // then
        BoardDetailResponseDto boardDetailResponseDto = boardService.getOneBoard(updateId);
        assertThat(updateId).isEqualTo(saveId);
        assertThat(boardDetailResponseDto.getTitle()).isEqualTo(updateTitle);
        assertThat(boardDetailResponseDto.getContent()).isEqualTo(updateContent);
        assertThat(boardDetailResponseDto.getEducationId()).isEqualTo(updateEducation.getId());
    }

    @Test
    @DisplayName("boardService.deleteOneBoard() 테스트 (단건 삭제)")
    public void deleteOneBoardTest() {
        // given
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder().title(title).content(content).userId(user.getId()).educationId(education.getId()).build();

        Long saveId = boardService.postOneBoard(boardRequestDto);

        // when
        Long deleteId = boardService.deleteOneBoard(saveId);

        // then
        assertThat(deleteId).isEqualTo(saveId);
        assertThat(boardRepository.findById(deleteId)).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("boardService.getAllBoard() 테스트 (전체 조회, 페이징)")
    public void getAllBoardTest() {
        // given
        int originalTotalNumberOfData = (int) boardRepository.count();
        int page = 0;
        int size = 10;
        String properties = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, properties);

        int targetIndex = originalTotalNumberOfData;
        int totalNumberOfData = 29;
        String title = "테스트 - BoardRequestDto title";
        String content = "테스트 - BoardRequestDto content";
        for (int i = 0; i < totalNumberOfData; i++) {
            boardService.postOneBoard(BoardRequestDto.builder().title(title).content(content).userId(user.getId()).educationId(education.getId()).build());
        }
        em.clear();
        em.flush();

        // when
        Page<BoardAllResponseDto> boardAllResponseDtos = boardService.getAllBoard(pageable);

        // then
        assertThat(boardAllResponseDtos.getTotalElements()).isEqualTo(totalNumberOfData);
        assertThat(boardAllResponseDtos.getContent().get(targetIndex).getTitle()).isEqualTo(title);
    }
}
