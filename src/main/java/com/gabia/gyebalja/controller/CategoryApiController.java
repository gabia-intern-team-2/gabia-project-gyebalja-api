package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.category.CategoryRequestDto;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Stream;

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
    @GetMapping("/api/v1/categories/{categoryId}")
    public CommonJsonFormat getOneCategory(@PathVariable("categoryId") Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.getOneCategory(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), categoryResponseDto);
    }

    /** 수정 - category 한 건 (카테고리 단건 수정) */
    @PutMapping("/api/v1/categories/{categoryId}")
    public CommonJsonFormat putOneCategory(@PathVariable("categoryId") Long id, @RequestBody CategoryRequestDto categoryRequestDto) {
        Long categoryId = categoryService.putOneCategory(id, categoryRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), categoryId);
    }

    /** 삭제 - category 한 건 (카테고리 단건 삭제) */
    @DeleteMapping("/api/v1/categories/{categoryId}")
    public CommonJsonFormat deleteOneCategory(@PathVariable("categoryId") Long id) {
        Long categoryId = categoryService.deleteOneCategory(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), categoryId);
    }

    /** 조회 - category 전체 조회 (페이징 x) */
    @GetMapping("/api/v1/categories")
    public CommonJsonFormat getAllCategory() {
        Stream<CategoryResponseDto> allCategory = categoryService.getAllCategory();

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), allCategory);
    }
}
