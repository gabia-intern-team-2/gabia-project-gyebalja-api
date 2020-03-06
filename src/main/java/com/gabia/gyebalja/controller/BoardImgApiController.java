package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.service.BoardImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class BoardImgApiController {

    private final BoardImgService boardImgService;

    /** 등록 - boardImg 한 건 (이미지 등록) */
    @PostMapping("/api/v1/boardImgs")
    public String postOneBoardImg(@RequestParam("image") MultipartFile image) throws IOException {
        String response = boardImgService.postOneBoardImg(image);

        return response;
    }
}
