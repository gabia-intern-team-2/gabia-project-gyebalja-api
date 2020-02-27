package com.gabia.gyebalja.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginApiController {
    /** 조회 - education 한 건 (상세페이지) */
    @CrossOrigin
    @GetMapping("/auth/callback")
    public void getLogin() {
        System.out.println("요청 확인");
    }

    @CrossOrigin
    @PostMapping("/auth/callback")
    public void postLogin() {
        System.out.println("요청 확인");
    }

    @CrossOrigin
    @RequestMapping(value="/auth/callback", method= RequestMethod.OPTIONS)
    public void getLoginCheck() {
        System.out.println("요청 확인");
    }
}
