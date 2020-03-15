package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Comment;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.comment.CommentRequestDto;
import com.gabia.gyebalja.dto.comment.CommentResponseDto;
import com.gabia.gyebalja.repository.BoardRepository;
import com.gabia.gyebalja.repository.CommentRepository;
import com.gabia.gyebalja.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : 이현재
 * Part : All
 */

@RequiredArgsConstructor
@Service
public class CommentService {

    @PersistenceContext
    EntityManager em;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /** 등록 - comment 한 건 (댓글 등록) */
    @Transactional
    public Long postOneComment(CommentRequestDto commentRequestDto){

        User user = userRepository.findById(commentRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        Board board = boardRepository.findById(commentRequestDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        Long commentId = commentRepository.save(Comment.builder().content(commentRequestDto.getContent()).user(user).board(board).build()).getId();

        return commentId;
    }

    /** 조회 - comment 한 건 (어디서 사용할 지 모르지만 일단 구현) */
    @Transactional
    public CommentResponseDto getOneComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        return new CommentResponseDto(comment);
    }

    /** 수정 - comment 한 건 */
    @Transactional
    public Long putOneComment(Long commentId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        // 더티 체킹
        comment.changeContent(commentRequestDto.getContent());
        em.flush();
        em.clear();

        return commentId;
    }

    /** 삭제 - comment 한 건 */
    @Transactional
    public Long deleteOneComment(Long commentId){
        commentRepository.deleteById(commentId);
        em.flush();
        em.clear();

        return commentId;
    }

    /** 조회 - comment 전체 */
    @Transactional
    public List<CommentResponseDto> getAllComment(Long boardId){
        List<Comment> comments = commentRepository.findByBoardId(boardId);
        List<CommentResponseDto> commentResponseDtos = comments.stream().map(comment -> new CommentResponseDto(comment)).collect(Collectors.toList());

        return commentResponseDtos;
    }
}
