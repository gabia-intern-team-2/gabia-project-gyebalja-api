package com.gabia.gyebalja.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class BoardImgApiController {

    //    public static final String uploadingDir = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
    public static final String uploadingDir = "/var/www/html/images/";

    /** 등록 - boardImg 한 건 (이미지 등록) */
    @PostMapping("/api/v1/boardImgs")
    public String postOneBoardImg(@RequestParam("image") MultipartFile image) throws IOException {
        int tryCount = 0;
        int tryThreshold = 100;
        int randomNumberRange = 10;

        String fileName = System.currentTimeMillis() + ((int) Math.random() * randomNumberRange) + image.getOriginalFilename();
        File file = new File(uploadingDir + fileName);
        for(; file.exists() && tryCount < tryThreshold; tryCount++){
            fileName = System.currentTimeMillis() + ((int) Math.random() * randomNumberRange) + image.getOriginalFilename();
            file = new File(uploadingDir + fileName);
        }
        if(tryCount >= tryThreshold) return "images/uploadfailed.jpg";

        image.transferTo(file);
        String imageUrl = "images/" + fileName;

        return imageUrl;
    }
}
