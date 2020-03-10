package com.gabia.gyebalja.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Author : 이현재
 * Part : postOneBoardImg()
 * Author : 정태균
 * Part : postOneUserImg()
 */

@Transactional
@Service
public class ImageService {

    public static final String boardImgDir = "/var/www/html/images/boards/";
    public static final String userImgDir = "/var/www/html/images/users/";

    public String postOneBoardImg(MultipartFile image) throws IOException {
        int tryCount = 0;
        int tryThreshold = 100;
        int randomNumberRange = 10;

        String fileName = System.currentTimeMillis() + ((int) Math.random() * randomNumberRange) + image.getOriginalFilename();
        File file = new File(boardImgDir + fileName);
        for(; file.exists() && tryCount < tryThreshold; tryCount++){
            fileName = System.currentTimeMillis() + ((int) Math.random() * randomNumberRange) + image.getOriginalFilename();
            file = new File(boardImgDir + fileName);
        }
        if(tryCount >= tryThreshold) return "http://api.gyeblja.com/images/boards/uploadfailed.jpg";

        image.transferTo(file);
        String imageUrl = "http://api.gyeblja.com/images/boards/" + fileName;

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
        if(tryCount >= tryThreshold) return "http://api.gyeblja.com/images/users/basic.jpg";

        image.transferTo(file);
        String imageUrl = "http://api.gyeblja.com/images/users/" + fileName;

        return imageUrl;
    }
}
