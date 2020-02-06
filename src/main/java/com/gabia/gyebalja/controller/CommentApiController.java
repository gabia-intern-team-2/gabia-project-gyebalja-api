//package com.gabia.gyebalja.controller;
//
//import com.gabia.gyebalja.dto.comment.CommentRequestDto;
//import com.gabia.gyebalja.dto.comment.CommentResponseDto;
//import com.gabia.gyebalja.service.CommentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RequiredArgsConstructor
//@RestController
//public class CommentApiController {
//
//    private final CommentService commentService;
//
//    /** 등록 - comment 한 건 */
//    @PostMapping("/api/v1/comments")
//    public Long postOneComment(@RequestBody CommentRequestDto commentRequestDto){
//        Long commentId = commentService.save(commentRequestDto);
//
//        return commentId;
//    }
//
//    /** 조회 - comment 한 건 */
//    @GetMapping("/api/v1/comments/{id}")
//    public CommentResponseDto getOneComment(@PathVariable("id") Long id){
//        CommentResponseDto commentResponseDto = commentService.findById(id);
//
//        return commentResponseDto;
//    }
//
//    /** 수정 - comment 한 건 */
//    @PutMapping("/api/v1/comments/{id}")
//    public Long putOneComment(@PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto){
//        Long commentId = commentService.update(id, commentRequestDto);
//
//        return commentId;
//    }
//
//    @DeleteMapping("/api/v1/comments/{id}")
//    public Long deleteOneComment(@PathVariable("id") Long id){
//        commentService.delete(id);  // 검토. try - catch?
//
//        return 200L;    // 검토. return 값 무엇으로?
//    }
//}
