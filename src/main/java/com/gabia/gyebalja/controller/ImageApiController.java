package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;

    /** 등록 - boardImg 한 건 (이미지 등록) */
    @PostMapping("/api/v1/boardImgs")
    public String postOneBoardImg(@RequestParam("image") MultipartFile image) throws IOException {
        String response = imageService.postOneBoardImg(image);

        return response;
    }

    /** 등록 - userImg 한 건 (이미지 등록) */
    @PostMapping("/api/v1/userImgs")
    public String postOneUserImg(@RequestParam("image") MultipartFile image) throws IOException {
        String response = imageService.postOneUserImg(image);

        return response;
    }
}
