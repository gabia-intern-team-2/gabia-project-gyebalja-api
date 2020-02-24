package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.board.BoardAllResponseDto;
import com.gabia.gyebalja.dto.board.BoardRequestDto;
import com.gabia.gyebalja.dto.board.BoardDetailResponseDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.LikesRepository;
import com.gabia.gyebalja.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {

    @PersistenceContext
    EntityManager em;

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final LikesRepository likesRepository;

    /** 등록 - board 한 건 (게시글 등록) */
    public Long postOneBoard(BoardRequestDto boardRequestDto){
        User user = userRepository.findById(boardRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        Education education = educationRepository.findById(boardRequestDto.getEducationId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        Long boardId = boardRepository.save(Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .views(0)
                .user(user)
                .education(education)
                .build()).getId();

        return boardId;
    }

    /** 조회 - board 한 건 (상세페이지) */
    public BoardDetailResponseDto getOneBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        board.upViews();
        BoardDetailResponseDto boardDetailResponseDto = new BoardDetailResponseDto(board);

        // 게시글 좋아요 조회 - boardDto 삽입
        int totalNumberOfLikes = likesRepository.countByBoardId(boardId);
        boardDetailResponseDto.changeLikes(totalNumberOfLikes);

        return boardDetailResponseDto;
    }

    /** 수정 - board 한 건 (상세페이지에서) */
    public Long putOneBoard(Long boardId, BoardRequestDto boardRequestDto){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        // 더티 체킹
        Education education = educationRepository.findById(boardRequestDto.getEducationId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        board.changeBoard(boardRequestDto, education);

        em.flush();
        em.clear();

        return boardId;
    }

    /** 삭제 - board 한 건 (상세페이지에서) */
    public Long deleteOneBoard(Long boardId){
        boardRepository.deleteById(boardId);
        em.flush();
        em.clear();

        return boardId;
    }

    /** 조회 - board 전체 (페이징) */
    public Page<BoardAllResponseDto> getAllBoard(Pageable pageable){
        /**
         * [ResponseDto]
         * 댓글 수, 좋아요 수 등 추가 가능
         * 댓글 배열 삭제 필요
         * */
        Page<Board> boardPage = boardRepository.findAll(pageable);
        Page<BoardAllResponseDto> boardAllResponseDtos = boardPage.map(board -> new BoardAllResponseDto(board));

        return boardAllResponseDtos;
    }
}
