package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.service.GabiaService;
import com.gabia.gyebalja.vo.GabiaTokenVo;
import com.gabia.gyebalja.vo.GabiaUserInfoVo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor  //생성자 주입방식을 사용하기 위해 사용
@RestController
public class GabiaLoginController {

   private final Environment env;
   private final RestTemplate restTemplate;
   private final Gson gson;
   private final GabiaService gabiaService;

   @Value("${spring.social.gabia.client_id}")
   private String gabiaClientId;

   @Value("${spring.social.gabia.client_secret}")
   private String gabiaClientSecret;

    /**
     * 하이웍스 로그인 클릭 시
     */
    @GetMapping("/api/v1/login")
    public CommonJsonFormat gabiaLogin() {
        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("spring.social.gabia.url.login"))
                .append("?client_id=").append(gabiaClientId)
                .append("&access_type=offline");
        System.out.println("loginUrl = " + loginUrl);
        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), loginUrl);
    }

    /**
     * 하이웍스 콜백
     */
    @GetMapping("/auth/callback")
    public RedirectView getAuthCode(@RequestParam("auth_code") String authCode, HttpServletResponse response) {
        GabiaTokenVo accessToken = gabiaService.getAccessToken(authCode);
        System.out.println("accessToken = " + accessToken.getAccess_token());
        GabiaUserInfoVo gabiaUserInfo = gabiaService.getGabiaProfile(accessToken.getAccess_token());
        // 토큰 기반 쿠키생성
        Cookie setCookie = new Cookie("access_token", accessToken.getAccess_token());
        setCookie.setMaxAge(60*60*24);
        response.addCookie(setCookie);

        return new RedirectView("http://localhost:8085");
    }
}
