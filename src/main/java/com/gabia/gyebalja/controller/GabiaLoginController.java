package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.CookieBox;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.service.GabiaService;
import com.gabia.gyebalja.service.JwtService;
import com.gabia.gyebalja.vo.GabiaTokenVo;
import com.gabia.gyebalja.vo.GabiaUserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author : 정태균
 * Part : All
 */

@RequiredArgsConstructor  //생성자 주입방식을 사용하기 위해 사용
@Api(value = "GabiaLoginController V1")
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
    @ApiOperation(value = "gabiaLogin : 하이웍스 로그인 클릭 시", notes = "하이웍스 로그인 페이지를 리턴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
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
    @ApiOperation(value = "getAuthCode : 콜백", notes = "하이웍스 로그인 후 콜백 URL (auth_code)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
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

        return new RedirectView("http://"+frontHost+":"+frontPort);
    }

    /**
     * DB에 등록 된 사용자인지 판별 요청
     * defaltvalue 필요 이유 : null이면 500에러를 반환하기 때문에
     */
    @ApiOperation(value = "isRegister : 로그인 시 등록된 사용자인지 판별", notes = "DB에 등록 된 사용자인지 판별")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/login/isRegister")
    public CommonJsonFormat isRegister(@CookieValue(value = "jwt_token", defaultValue = "no_cookie") String jwtToken) {
        boolean isRegister = jwtService.isRegister(jwtToken);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), isRegister);
    }

    /**
     * 인증 된 사용자인지 판별 요청
     */
    @ApiOperation(value = "authUserCheck : 인증 된 사용자인지 판별 요청", notes = "인증 된 사용자인지 판별 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/auth/user")
    public CommonJsonFormat authUserCheck(@CookieValue(value = "jwt_token", defaultValue = "no_cookie") String jwtToken) {
        boolean authUser = jwtService.isUsable(jwtToken);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), authUser);
    }

    /**
     * 로그아웃 요청
     */
    @ApiOperation(value = "logOut : 로그아웃 요청", notes = "로그아웃 요청 - 쿠키 삭제 토큰 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/logout")
    public CommonJsonFormat logOut(HttpServletRequest request, HttpServletResponse response) {
        String message = jwtService.destroyToken(request, response);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), message);
    }
}
