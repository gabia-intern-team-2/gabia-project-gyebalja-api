package com.gabia.gyebalja.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class BoardImgApiController {

    public static final String uploadingDir = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";

    /** 등록 - boardImg 한 건 (이미지 등록) */
    @PostMapping("/api/v1/boardImgs")
    public String postOneBoardImg(@RequestParam("image") MultipartFile image) throws IOException {
        byte[] data = image.getBytes();
        File file = new File(uploadingDir + image.getOriginalFilename());
        image.transferTo(file);

        String imageUrl = "/static/images/" + image.getOriginalFilename();

        return imageUrl;
    }
}
