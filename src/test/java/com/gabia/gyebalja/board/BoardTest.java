package com.gabia.gyebalja.board;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.dto.BoardDto;
import com.gabia.gyebalja.dto.CommentDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CommentRepository;
import com.gabia.gyebalja.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Rollback(true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardTest {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BoardService boardService;
    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    /** 등록 - board 한 건 (게시글 등록) */
    @Test
    public void postOneBoard(){
        // given
        String title = "제목";
        String content = "본문";
        String url = "http://localhost:" + port + "/api/v1/boards";

        BoardDto boardDto = BoardDto.builder().title(title).content(content).build();

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, boardDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        Board board = boardRepository.findById(responseEntity.getBody()).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
    }

    /** 조회 - board 한 건 (상세페이지) */
    @Test
    public void getOneBoard(){
        // given
        Long targetId = 1L;
        String url = "http://localhost:" + port + "/api/v1/boards/" + targetId;

        Board board = boardRepository.findById(targetId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        BoardDto boardDto = new BoardDto(board);

        // when
        ResponseEntity<BoardDto> responseEntity = restTemplate.getForEntity(url, BoardDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(boardDto.getTitle());
        assertThat(responseEntity.getBody().getContent()).isEqualTo(boardDto.getContent());
        assertThat(responseEntity.getBody().getCommentList().size()).isEqualTo(commentRepository.findByBoardId(targetId).size());    // 게시글의 댓글 테스트 (개수로 테스트)
    }

    /** 수정 - board 한 건 (상세페이지에서) */
    @Test
    public void putOneBoard(){
        // given
        Board savedBoard = boardRepository.save(Board.builder().title("title").content("content").build());
        Long updateId = savedBoard.getId();
        String updateTitle = "updated title";
        String updateContent = "updated content";
        String url = "http://localhost:" + port + "/api/v1/boards/" + updateId;

        BoardDto boardDto = BoardDto.builder().title(updateTitle).content(updateContent).build();
        HttpEntity<BoardDto> requestEntity = new HttpEntity<>(boardDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class); // restTemplate.put(url, boardDto)

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        Board board = boardRepository.findById(updateId).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));
        assertThat(board.getTitle()).isEqualTo(updateTitle);
        assertThat(board.getContent()).isEqualTo(updateContent);
    }

    /** 삭제 - board 한 건 (상세페이지에서) */
    @Test
    public void deleteOneBoard(){
        //given
        long totalCount = boardRepository.count();
        Board savedBoard = boardRepository.save(Board.builder().title("title").content("content").build());
        Long deleteId = savedBoard.getId();
        String url = "http://localhost:" + port + "/api/v1/boards/" + deleteId;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(headers);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        assertThat(boardRepository.count()).isEqualTo(totalCount);

    }
}
