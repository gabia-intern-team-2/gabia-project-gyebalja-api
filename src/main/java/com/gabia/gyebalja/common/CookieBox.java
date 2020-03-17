package com.gabia.gyebalja.common;

import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : 정태균
 * Part : All
 */

@NoArgsConstructor
public class CookieBox {

    private Map cookieMap = new HashMap<>();

    /**
     * 생성자 : request로 부터 cookie 배열을 map으로 변환
     */
    public CookieBox(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0 ; i < cookies.length ; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
    }

    /**
     * 쿠키 세팅
     */
    public static Cookie createCookie(String name, String value, String domain, String path, int maxAge) {

        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(true);  // XSS 공격 방어를 위해 설정
        cookie.setMaxAge(maxAge);

        return cookie;
    }

    /**
     * 쿠키 조회
     */
    public Cookie getCookie(String name) {
        return (Cookie)cookieMap.get(name);
    }

    /**
     * 쿠키 값 조회
     */
    public String getValue(String name) throws IOException {
        Cookie cookie = (Cookie)cookieMap.get(name);
        if (cookie == null) return null;
        return cookie.getValue();
    }

    /**
     * 쿠키 존재 여부
     */
    public boolean exists(String name) {
        return cookieMap.get(name) != null;
    }

    /**
     * 쿠키 삭제
     */
    public Cookie deleteCookie(String cookieKey, Cookie cookie){

        cookie = getCookie(cookieKey);

        if(cookie.getPath() != null){
            cookie.setPath(cookie.getPath());
        }else{
            cookie.setPath("/");
        }
        if(cookie.getDomain() != null){
            cookie.setDomain(cookie.getDomain());
        }
        cookie.setMaxAge(0);

        return cookie;
    }
}
