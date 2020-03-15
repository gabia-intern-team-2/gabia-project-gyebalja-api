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

/**
 * Author : 이현재
 * Part : All
 */

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /** 등록 - comment 한 건 */
    @PostMapping("/api/v1/comments")
    public CommonJsonFormat postOneComment(@RequestBody CommentRequestDto commentRequestDto){
        Long response = commentService.postOneComment(commentRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 조회 - comment 한 건 */
    @GetMapping("/api/v1/comments/{commentId}")
    public CommonJsonFormat getOneComment(@PathVariable("commentId") Long commentId){
        CommentResponseDto response = commentService.getOneComment(commentId);
        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 수정 - comment 한 건 */
    @PutMapping("/api/v1/comments/{commentId}")
    public CommonJsonFormat putOneComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        Long response = commentService.putOneComment(commentId, commentRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    @DeleteMapping("/api/v1/comments/{commentId}")
    public CommonJsonFormat deleteOneComment(@PathVariable("commentId") Long commentId){
        Long response = commentService.deleteOneComment(commentId);  // 검토. try - catch?

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }
}
