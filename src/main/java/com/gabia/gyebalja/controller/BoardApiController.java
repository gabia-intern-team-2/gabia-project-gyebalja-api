package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.board.BoardAllResponseDto;
import com.gabia.gyebalja.dto.board.BoardRequestDto;
import com.gabia.gyebalja.dto.board.BoardDetailResponseDto;
import com.gabia.gyebalja.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
@Api(value = "BoardApiController V1")
@RestController
public class BoardApiController {

    private final BoardService boardService;

    /** 등록 - board 한 건 (게시글 등록) */
    @ApiOperation(value = "postOneBoard : 등록 - board 한 건 (게시글 등록)", notes = "게시글 한 건 등록 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/api/v1/boards")
    public CommonJsonFormat postOneBoard(@RequestBody BoardRequestDto boardRequestDto){
        Long response = boardService.postOneBoard(boardRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 조회 - board 한 건 (상세페이지) */
    @ApiOperation(value = "getOneBoard : 조회 - board 한 건 (상세페이지)", notes = "게시글 한 건 조회 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/boards/{boardId}")
    public CommonJsonFormat getOneBoard(@PathVariable("boardId") Long boardId) {
        BoardDetailResponseDto response = boardService.getOneBoard(boardId);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 수정 - board 한 건 (상세페이지에서) */
    @ApiOperation(value = "putOneBoard : 수정 - board 한 건 (상세페이지에서)", notes = "게시글 한 건 수정 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PutMapping("/api/v1/boards/{boardId}")
    public CommonJsonFormat putOneBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardRequestDto boardRequestDto){
        Long response = boardService.putOneBoard(boardId, boardRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 삭제 - board 한 건 (상세페이지에서) */
    @ApiOperation(value = "deleteOneBoard : 삭제 - board 한 건 (상세페이지에서)", notes = "게시글 한 건 삭제 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @DeleteMapping("/api/v1/boards/{boardId}")
    public CommonJsonFormat deleteOneBoard(@PathVariable("boardId") Long boardId){
        Long response = boardService.deleteOneBoard(boardId);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 조회 - board 전체 (페이징) */
    @ApiOperation(value = "getAllBoard : 조회 - board 전체 (페이징)", notes = "게시글 전체 조회 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/boards")
    public CommonJsonFormat getAllBoard(@PageableDefault(size=100, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        // Example - http://localhost:8080/api/v1/boards?page=0&size=4&sort=id,desc
        Page<BoardAllResponseDto> response = boardService.getAllBoard(pageable);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }
}
