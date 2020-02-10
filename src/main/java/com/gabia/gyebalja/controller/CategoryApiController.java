package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.category.CategoryRequestDto;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.service.CategoryService;
import com.gabia.gyebalja.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CategoryApiController {

    private final CategoryService categoryService;

    /** 등록 - category 한 건 (카테고리 등록) */
    @PostMapping("/api/v1/categories")
    public CommonJsonFormat postOneCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        Long categoryId = categoryService.postOneCategory(categoryRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), categoryId);
    }

    /** 조회 - category 한 건 (카테고리 단건 조회) */
    @GetMapping("/api/v1/categories/{categoryid}")
    public CommonJsonFormat getOneCategory(@PathVariable("categoryid") Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.getOneCategory(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), categoryResponseDto);
    }
}
