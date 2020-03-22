package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.likes.LikesRequestDto;
import com.gabia.gyebalja.dto.likes.LikesResponseDto;
import com.gabia.gyebalja.service.LikesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : 이현재
 * Part : All
 */

@RequiredArgsConstructor
@Api(value = "LikesApiController V1")
@RestController
public class LikesApiController {

    private final LikesService likesService;

    /** 등록 - likes 한 개 */
    @ApiOperation(value = "postOneLikes : 등록 - likes 한 개", notes = "좋아요 등록 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/api/v1/likes")
    public CommonJsonFormat postOneLikes(@RequestBody LikesRequestDto likesRequestDto){
        Long response = likesService.postOneLikes(likesRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 조회 - likes 한 개 */
    @ApiOperation(value = "getOneLikes : 조회 - likes 한 개", notes = "좋아요 조회 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/likes/users/{userId}/boards/{boardId}")
    public CommonJsonFormat getOneLikes(@PathVariable("userId") Long userId, @PathVariable("boardId") Long boardId){
        LikesResponseDto response = likesService.getOneLikes(userId, boardId);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 삭제 - likes 한 개 */
    @ApiOperation(value = "deleteOneLikes : 삭제 - likes 한 개", notes = "좋아요 취소(삭제) 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @DeleteMapping("/api/v1/likes/users/{userId}/boards/{boardId}")
    public CommonJsonFormat deleteOneLikes(@PathVariable("userId") Long userId, @PathVariable("boardId") Long boardId){
        Long response = likesService.deleteOneLikes(userId, boardId);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }
}
