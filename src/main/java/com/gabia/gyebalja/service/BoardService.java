package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Comment;
import com.gabia.gyebalja.dto.BoardDto;
import com.gabia.gyebalja.dto.CommentDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    /** 등록 - board 한 건 (게시글 등록) */
    @Transactional
    public Long save(BoardDto boardDto){
        Long boardId = boardRepository.save(boardDto.toEntity()).getId(); // 검토. getId() 적절한지?

        return boardId;
    }

    /** 조회 - board 한 건 (상세페이지) */
    @Transactional
    public BoardDto findById(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));    // 검토. 404 Error?
        BoardDto boardDto = new BoardDto(board);

        // 게시글에 속한 댓글 조회, boardDto 에 삽입
        List<Comment> commentList = commentRepository.findByBoardId(id);
        List<CommentDto> commentDtoList = commentList.stream().map(comment -> new CommentDto(comment)).collect(Collectors.toList());

        boardDto.changeCommentList(commentDtoList);

        return boardDto;
    }

    /** 수정 - board 한 건 (상세페이지에서) */
    @Transactional
    public Long update(Long id, BoardDto boardDto){
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));    // 검토. 404 Error?

        // 더티 체킹
        board.changeTitle(boardDto.getTitle());
        board.changeContent(boardDto.getContent());

        return id;
    }

    /** 삭제 - board 한 건 (상세페이지에서) */
    @Transactional
    public Long delete(Long id){
        boardRepository.deleteById(id);

        return id;
    }
}
