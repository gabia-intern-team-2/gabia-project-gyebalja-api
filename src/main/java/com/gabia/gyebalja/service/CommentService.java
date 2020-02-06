package com.gabia.gyebalja.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
//
//    private final CommentRepository commentRepository;
//
//    /** 등록 - comment 한 건 (댓글 등록) */
//    @Transactional
//    public Long save(GenderType GenderType){
//        System.out.println("SERVICE START");
//        Long commentId = commentRepository.save(GenderType.toEntity()).getId();
//        System.out.println("SERVICE END");
//        return commentId;
//    }
//
//    /** 조회 - comment 한 건 (어디서 사용할 지 모르지만) */
//    @Transactional
//    public GenderType findById(Long id){
//        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
//
//        return new GenderType(comment);
//    }
//
//    /** 수정 - comment 한 건 */
//    @Transactional
//    public Long update(Long id, GenderType GenderType){
//        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
//        // 더티 체킹
//        comment.changeContent(GenderType.getContent());
//
//        return id;
//    }
//
//    /** 삭제 - comment 한 건 */
//    @Transactional
//    public Long delete(Long id){
//        commentRepository.deleteById(id);
//
//        return id;
//    }
}
