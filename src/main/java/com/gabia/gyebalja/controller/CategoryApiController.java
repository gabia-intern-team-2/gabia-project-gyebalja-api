package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.category.CategoryRequestDto;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.service.CategoryService;
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
 * Author : 정태균
 * Part : All
 */

@RequiredArgsConstructor
@Api(value = "CategoryApiController V1")
@RestController
public class CategoryApiController {

    private final CategoryService categoryService;

    /** 등록 - category 한 건 (카테고리 등록) */
    @ApiOperation(value = "postOneCategory : 등록 - category 한 건 (카테고리 등록)", notes = "카테고리 한 건 등록 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/api/v1/categories")
    public CommonJsonFormat postOneCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        Long categoryId = categoryService.postOneCategory(categoryRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), categoryId);
    }

    /** 조회 - category 한 건 (카테고리 단건 조회) */
    @ApiOperation(value = "getOneCategory : 조회 - category 한 건 (카테고리 단건 조회)", notes = "카테고리 한 건 조회 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/categories/{categoryId}")
    public CommonJsonFormat getOneCategory(@PathVariable("categoryId") Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.getOneCategory(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), categoryResponseDto);
    }

    /** 수정 - category 한 건 (카테고리 단건 수정) */
    @ApiOperation(value = "putOneCategory : 수정 - category 한 건 (카테고리 단건 수정)", notes = "카테고리 한 건 수정 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PutMapping("/api/v1/categories/{categoryId}")
    public CommonJsonFormat putOneCategory(@PathVariable("categoryId") Long id, @RequestBody CategoryRequestDto categoryRequestDto) {
        Long categoryId = categoryService.putOneCategory(id, categoryRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), categoryId);
    }

    /** 삭제 - category 한 건 (카테고리 단건 삭제) */
    @ApiOperation(value = "deleteOneCategory : 삭제 - category 한 건 (카테고리 단건 삭제)", notes = "카테고리 한 건 삭제 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @DeleteMapping("/api/v1/categories/{categoryId}")
    public CommonJsonFormat deleteOneCategory(@PathVariable("categoryId") Long id) {
        Long categoryId = categoryService.deleteOneCategory(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), categoryId);
    }

    /** 조회 - category 전체 조회 (페이징 x) */
    @ApiOperation(value = "getAllCategory : 조회 - category 전체 조회 (페이징 x)", notes = "카테고리 전체 조회 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/categories")
    public CommonJsonFormat getAllCategory() {
        List<CategoryResponseDto> allCategory = categoryService.getAllCategory();

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), allCategory);
    }
}
