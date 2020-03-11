package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.CookieBox;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.service.GabiaService;
import com.gabia.gyebalja.service.JwtService;
import com.gabia.gyebalja.vo.GabiaTokenVo;
import com.gabia.gyebalja.vo.GabiaUserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Author : 정태균
 * Part : All
 */

@RequiredArgsConstructor  //생성자 주입방식을 사용하기 위해 사용
@RestController
public class GabiaLoginController {

    @Autowired
    JwtService jwtService;

    private final Environment env;
    private final GabiaService gabiaService;

    @Value("${spring.social.gabia.client_id}")
    private String gabiaClientId;

    @Value("${spring.domain.host}")
    private String frontHost;

    @Value("${spring.domain.port}")
    private String frontPort;

    /**
     * 하이웍스 로그인 클릭 시
     */
    @GetMapping("/api/v1/login")
    public CommonJsonFormat gabiaLogin() {
        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("spring.social.gabia.url.login"))
                .append("?client_id=").append(gabiaClientId)
                .append("&access_type=offline");
        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), loginUrl);
    }

    /**
     * 하이웍스 콜백
     */
    @GetMapping("/auth/callback")
    public RedirectView getAuthCode(@RequestParam("auth_code") String authCode, HttpServletResponse response) {
        GabiaTokenVo accessToken = gabiaService.getAccessToken(authCode);
        GabiaUserInfoVo gabiaUserInfo = gabiaService.getGabiaProfile(accessToken.getAccess_token());
        //토큰 생성
        String jwtToken = jwtService.createToken(gabiaUserInfo);
        // 토큰 기반 쿠키생성
        CookieBox cookieBox = new CookieBox();

        Cookie setCookie = cookieBox.createCookie("jwt_token", jwtToken, frontHost, "/", 60*60*3);
        response.addCookie(setCookie);

        return new RedirectView(frontHost+":"+frontPort);
    }

    /**
     * DB에 등록 된 사용자인지 판별 요청
     * defaltvalue 필요 이유 : null이면 500에러를 반환하기 때문에
     */
    @GetMapping("/api/v1/login/isRegister")
    public CommonJsonFormat isRegister(@CookieValue(value = "jwt_token", defaultValue = "no_cookie") String jwtToken) {
        boolean isRegister = jwtService.isRegister(jwtToken);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), isRegister);
    }

    /**
     * 인증 된 사용자인지 판별 요청
     */
    @GetMapping("/api/v1/auth/user")
    public CommonJsonFormat authUserCheck(@CookieValue(value = "jwt_token", defaultValue = "no_cookie") String jwtToken) {
        boolean authUser = jwtService.isUsable(jwtToken);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), authUser);
    }

    /**
     * 로그아웃 요청
     */
    @GetMapping("/api/v1/logout")
    public CommonJsonFormat logOut(HttpServletResponse response) {
        String message = jwtService.destroyToken(response);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), message);
    }
}
