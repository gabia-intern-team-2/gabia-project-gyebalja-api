package com.gabia.gyebalja.interceptor;

import com.gabia.gyebalja.exception.UnauthorizedException;
import com.gabia.gyebalja.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        final String token = cookies[0].getValue();

        if(token != null && jwtService.isUsable(token)){
            return true;
        }else{
            throw new UnauthorizedException();
        }

    }
}
