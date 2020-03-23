package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Author : 이현재
 * Part : postOneBoardImg()
 * Author : 정태균
 * Part : postOneUserImg()
 */

@RequiredArgsConstructor
@Api(value = "ImageApiController V1")
@RestController
public class ImageApiController {

    private final ImageService imageService;

    /** 등록 - boardImg 한 건 (이미지 등록) */
    @ApiOperation(value = "postOneBoardImg :  등록 - boardImg 한 건 (이미지 등록)", notes = "게시글 작성 시 이미지 저장 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/api/v1/boardImgs")
    public String postOneBoardImg(@RequestParam("image") MultipartFile image) throws IOException {
        String response = imageService.postOneBoardImg(image);

        return response;
    }

    /** 등록 - userImg 한 건 (이미지 등록) */
    @ApiOperation(value = "postOneUserImg :  등록 - userImg 한 건 (이미지 등록)", notes = "사용자 정보 저장, 수정 시  사용자 이미지 저장 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/api/v1/userImgs")
    public String postOneUserImg(@RequestParam("image") MultipartFile image) throws IOException {
        String response = imageService.postOneUserImg(image);

        return response;
    }
}
