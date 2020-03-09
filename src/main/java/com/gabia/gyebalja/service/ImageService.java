package com.gabia.gyebalja.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Transactional
@Service
public class ImageService {

    public static final String uploadingDir = "/var/www/html/images/";
    public static final String userImgDir = "/var/www/html/images/users/";

    public String postOneBoardImg(MultipartFile image) throws IOException {
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

    public String postOneUserImg(MultipartFile image) throws IOException {
        int tryCount = 0;
        int tryThreshold = 100;
        int randomNumberRange = 10;

        String fileName = System.currentTimeMillis() + ((int) Math.random() * randomNumberRange) + image.getOriginalFilename();
        File file = new File(userImgDir + fileName);
        for(; file.exists() && tryCount < tryThreshold; tryCount++){
            fileName = System.currentTimeMillis() + ((int) Math.random() * randomNumberRange) + image.getOriginalFilename();
            file = new File(userImgDir + fileName);
        }
        if(tryCount >= tryThreshold) return "images/uploadfailed.jpg";

        image.transferTo(file);
        String imageUrl = "http://api.gyeblja.com/images/users/" + fileName;

        return imageUrl;
    }
}
