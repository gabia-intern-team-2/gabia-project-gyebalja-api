package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.dto.CommentDto;
import com.gabia.gyebalja.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /** 등록 - comment 한 건 */
    @PostMapping("/api/v1/comments")
    public Long postOneComment(@RequestBody CommentDto commentDto){
        Long commentId = commentService.save(commentDto);

        return commentId;
    }

    /** 조회 - comment 한 건 */
    @GetMapping("/api/v1/comments/{id}")
    public CommentDto getOneComment(@PathVariable("id") Long id){
        CommentDto commentDto = commentService.findById(id);

        return commentDto;
    }

    /** 수정 - comment 한 건 */
    @PutMapping("/api/v1/comments/{id}")
    public Long putOneComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto){
        Long commentId = commentService.update(id, commentDto);

        return commentId;
    }

    @DeleteMapping("/api/v1/comments/{id}")
    public Long deleteOneComment(@PathVariable("id") Long id){
        commentService.delete(id);  // 검토. try - catch?

        return 200L;    // 검토. return 값 무엇으로?
    }
}
