package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.likes.LikesRequestDto;
import com.gabia.gyebalja.dto.likes.LikesResponseDto;
import com.gabia.gyebalja.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
public class LikesApiController {

    private final LikesService likesService;

    /** 등록 - likes 한 개 */
    @PostMapping("/api/v1/likes")
    public CommonJsonFormat postOneLikes(@RequestBody LikesRequestDto likesRequestDto){
        LikesResponseDto likesResponseDto = likesService.save(likesRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(),likesResponseDto);
    }

    /** 삭제 - likes 한 개 */
    @DeleteMapping("/api/v1/likes/users/{userId}/boards/{boardId}")
    public CommonJsonFormat deleteOneLikes(@PathVariable("userId") Long userId, @PathVariable("boardId") Long boardId){
        likesService.delete(userId, boardId);

        return new CommonJsonFormat(StatusCode.NO_CONTENT.getCode(), StatusCode.NO_CONTENT.getMessage(), new ArrayList<>());
    }
}
