package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.comment.CommentRequestDto;
import com.gabia.gyebalja.dto.comment.CommentResponseDto;
import com.gabia.gyebalja.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author : 이현재
 * Part : All
 */

@RequiredArgsConstructor
@Api(value = "CommentApiController V1")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /** 등록 - comment 한 건 */
    @ApiOperation(value = "postOneComment : 등록 - comment 한 건", notes = "게시글에 대한 댓글 등록 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/api/v1/comments")
    public CommonJsonFormat postOneComment(@RequestBody CommentRequestDto commentRequestDto){
        Long response = commentService.postOneComment(commentRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 조회 - comment 한 건 */
    @ApiOperation(value = "getOneComment : 조회 - comment 한 건", notes = "댓글 단 건 조회 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/comments/{commentId}")
    public CommonJsonFormat getOneComment(@PathVariable("commentId") Long commentId){
        CommentResponseDto response = commentService.getOneComment(commentId);
        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 수정 - comment 한 건 */
    @ApiOperation(value = "putOneComment : 수정 - comment 한 건", notes = "댓글 단 건 수정 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PutMapping("/api/v1/comments/{commentId}")
    public CommonJsonFormat putOneComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        Long response = commentService.putOneComment(commentId, commentRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 삭제 - comment 한 건 */
    @ApiOperation(value = "deleteOneComment : 삭제 - comment 한 건", notes = "댓글 단 건 삭제 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @DeleteMapping("/api/v1/comments/{commentId}")
    public CommonJsonFormat deleteOneComment(@PathVariable("commentId") Long commentId){
        Long response = commentService.deleteOneComment(commentId);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 조회 - comment 전체 */
    @ApiOperation(value = "getAllComment : 조회 - comment 전체", notes = "게시글에 대한 전체 댓글 조회 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/boards/{boardId}/comments")
    public CommonJsonFormat getAllComment(@PathVariable("boardId") Long boardId){
        List<CommentResponseDto> response = commentService.getAllComment(boardId);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }
}
