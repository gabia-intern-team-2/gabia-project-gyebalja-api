package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.tag.TagRequestDto;
import com.gabia.gyebalja.dto.tag.TagResponseDto;
import com.gabia.gyebalja.service.TagService;
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
@RestController
public class TagApiController {

    private final TagService tagService;

    /** 등록 - tag 한 건 (태그 등록) */
    @PostMapping("/api/v1/tags")
    public CommonJsonFormat postOneTag(@RequestBody TagRequestDto tagRequestDto) {
        Long tagId = tagService.postOneTag(tagRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), tagId);
    }

    /** 조회 - tag 한 건 (태그 단건 조회) */
    @GetMapping("/api/v1/tags/{tagId}")
    public CommonJsonFormat getOneTag(@PathVariable("tagId") Long id) {
        TagResponseDto tagResponseDto = tagService.getOneTag(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), tagResponseDto);
    }

    /** 수정 - tag 한 건 (태그 단건 수정) */
    @PutMapping("/api/v1/tags/{tagId}")
    public CommonJsonFormat putOneTag(@PathVariable("tagId") Long id, @RequestBody TagRequestDto tagRequestDto) {
        Long tagId = tagService.putOneTag(id, tagRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), tagId);
    }

    /** 삭제 - tag 한 건 (태그 단건 삭제) */
    @DeleteMapping("/api/v1/tags/{tagId}")
    public CommonJsonFormat deleteOneTag(@PathVariable("tagId") Long id) {
        Long tagId = tagService.deleteOneTag(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), tagId);
    }

    /** 조회 - tag 전체 조회 (페이징 x) */
    @GetMapping("/api/v1/tags")
    public CommonJsonFormat getAllTag() {
        List<TagResponseDto> allTag = tagService.getAllTag();

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), allTag);
    }
}
