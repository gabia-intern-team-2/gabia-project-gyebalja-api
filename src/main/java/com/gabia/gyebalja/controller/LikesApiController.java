package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.dto.likes.LikesRequestDto;
import com.gabia.gyebalja.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikesApiController {

    private final LikesService likesService;

    /** 등록 - likes 한 개 */
    @PostMapping("/api/v1/likes")
    public Long postOneLikes(@RequestBody LikesRequestDto likesRequestDto){
        Long likesId = likesService.save(likesRequestDto);

        return likesId;
    }

    /** 삭제 - likes 한 개 */
    @DeleteMapping("/api/v1/likes/users/{userId}/boards/{boardId}")
    public Long deleteOneLikes(@PathVariable("userId") Long userId, @PathVariable("boardId") Long boardId){
        likesService.delete(userId, boardId);

        return 200L;
    }
}
