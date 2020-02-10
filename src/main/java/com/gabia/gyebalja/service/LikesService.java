package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Likes;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.likes.LikesRequestDto;
import com.gabia.gyebalja.dto.likes.LikesResponseDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.LikesRepository;
import com.gabia.gyebalja.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Transactional
@Service
public class LikesService {

    @PersistenceContext
    EntityManager em;

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /** 등록 - likes 한 개 */
    public LikesResponseDto save(LikesRequestDto likesRequestDto){
        User user = userRepository.findById(likesRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        Board board = boardRepository.findById(likesRequestDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        Likes likes = likesRepository.save(Likes.builder().user(user).board(board).build());

        return new LikesResponseDto(likes);
    }

    /** 삭제 - likes 한 개 */
    public Long delete(Long userId, Long boardId){
        likesRepository.deleteByUserIdAndBoardId(userId, boardId);
        em.flush();
        em.clear();

        // 검토 - (임시) 200L
        return 200L;
    }
}
