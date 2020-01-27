package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.dto.BoardDto;
import com.gabia.gyebalja.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

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

        return new BoardDto(board);
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
