package com.gabia.gyebalja.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
//
//    private final CommentService commentService;
//
//    /** 등록 - comment 한 건 */
//    @PostMapping("/api/v1/comments")
//    public Long postOneComment(@RequestBody GenderType GenderType){
//        Long commentId = commentService.save(GenderType);
//
//        return commentId;
//    }
//
//    /** 조회 - comment 한 건 */
//    @GetMapping("/api/v1/comments/{id}")
//    public GenderType getOneComment(@PathVariable("id") Long id){
//        GenderType GenderType = commentService.findById(id);
//
//        return GenderType;
//    }
//
//    /** 수정 - comment 한 건 */
//    @PutMapping("/api/v1/comments/{id}")
//    public Long putOneComment(@PathVariable("id") Long id, @RequestBody GenderType GenderType){
//        Long commentId = commentService.update(id, GenderType);
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
}
