package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.comment.CommentRequestDto;
import com.gabia.gyebalja.dto.comment.CommentResponseDto;
import com.gabia.gyebalja.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /** 등록 - comment 한 건 */
    @PostMapping("/api/v1/comments")
    public CommonJsonFormat postOneComment(@RequestBody CommentRequestDto commentRequestDto){
        Long commentId = commentService.save(commentRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), commentId);
    }

    /** 조회 - comment 한 건 */
    @GetMapping("/api/v1/comments/{id}")
    public CommonJsonFormat getOneComment(@PathVariable("id") Long id){
        CommentResponseDto commentResponseDto = commentService.findById(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), commentResponseDto);
    }

    /** 수정 - comment 한 건 */
    @PutMapping("/api/v1/comments/{id}")
    public CommonJsonFormat putOneComment(@PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto){
        Long commentId = commentService.update(id, commentRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), commentId);
    }

    @DeleteMapping("/api/v1/comments/{id}")
    public CommonJsonFormat deleteOneComment(@PathVariable("id") Long id){
        commentService.delete(id);  // 검토. try - catch?

        return new CommonJsonFormat(StatusCode.NO_CONTENT.getCode(), StatusCode.NO_CONTENT.getMessage(), 200L);
    }
}
