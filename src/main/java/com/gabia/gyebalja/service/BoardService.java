package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Comment;
import com.gabia.gyebalja.dto.board.BoardRequestDto;
import com.gabia.gyebalja.dto.board.BoardResponseDto;
import com.gabia.gyebalja.dto.comment.CommentResponseDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CommentRepository;
import com.gabia.gyebalja.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    @PersistenceContext
    EntityManager em;

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;


    /** 등록 - board 한 건 (게시글 등록) */
    @Transactional
    public Long postOneBoard(BoardRequestDto boardRequestDto){
        Long boardId = boardRepository.save(boardRequestDto.toEntity()).getId();

        return boardId;
    }

    /** 조회 - board 한 건 (상세페이지) */
    @Transactional
    public BoardResponseDto getOneBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
  
        // 더티 체킹
        // 게시글의 조회수 UP, boardDto 에 삽입
        board.upViews();
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        // 게시글의 댓글 조회, boardDto 에 삽입
        List<Comment> comments = commentRepository.findByBoardId(boardId);
        List<CommentResponseDto> commentResponseDtos = comments.stream().map(comment -> new CommentResponseDto(comment)).collect(Collectors.toList());
        boardResponseDto.changeCommentList(commentResponseDtos);

        // 게시글의 좋아요 조회, boardDto 에 삽입
        int totalNumberOfLikes = likesRepository.countByBoardId(boardId);
        boardResponseDto.changeLikes(totalNumberOfLikes);

        return boardResponseDto;
    }

    /** 수정 - board 한 건 (상세페이지에서) */
    @Transactional
    public Long putOneBoard(Long boardId, BoardRequestDto boardRequestDtoDto){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 더티 체킹
        board.changeTitle(boardRequestDtoDto.getTitle());
        board.changeContent(boardRequestDtoDto.getContent());
        em.flush();
        em.clear();

        return boardId;
    }

    /** 삭제 - board 한 건 (상세페이지에서) */
    @Transactional
    public Long deleteOneBoard(Long boardId){
        boardRepository.deleteById(boardId);
        em.flush();
        em.clear();

        return boardId;
    }

    /** 조회 - board 전체 (페이징) */
    @Transactional
    public Page<BoardResponseDto> getAllBoard(Pageable pageable){
        // 추후, 필요에 따라 댓글 개수, 좋아요 개수 등을 삽입하는 로직 추가
        Page<Board> boardPage = boardRepository.findAll(pageable);
        Page<BoardResponseDto> boardResponseDtoPage = boardPage.map(board -> new BoardResponseDto(board));

        return boardResponseDtoPage;
    }
}
