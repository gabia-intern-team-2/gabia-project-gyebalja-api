package com.gabia.gyebalja.common.interceptor;

import com.gabia.gyebalja.common.CookieBox;
import com.gabia.gyebalja.exception.UnauthorizedException;
import com.gabia.gyebalja.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Author : 정태균
 * Part : All
 */

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (env.getProperty("spring.profiles.active.test").equals("true")) return true;

        if(request.getMethod().equals(HttpMethod.OPTIONS.name())) return true;

        CookieBox cookieBox = new CookieBox(request);
        String token = null;
        if(cookieBox.exists("jwt_token")) {
            token = cookieBox.getValue("jwt_token");
        }
        // Swagger 쿠키인증은 아직 미지원 따라서 헤더에 JWT토큰을 넣어서 보낼 때 인증 처리 하기위함
        if(request.getHeader("jwt_token") != null)
            token = request.getHeader("jwt_token");

        if(token != null && jwtService.isUsable(token)){
            return true;
        }else{
            throw new UnauthorizedException();
        }
    }
}
