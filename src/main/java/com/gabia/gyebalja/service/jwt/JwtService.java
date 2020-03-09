package com.gabia.gyebalja.service.jwt;

import com.gabia.gyebalja.dto.user.UserResponseDto;
import com.gabia.gyebalja.vo.GabiaUserInfoVo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface JwtService {
    <T> String createToken(T data);
    Map<String, Object> get(String key);
    boolean isUsable(String jwt);
    boolean isRegister(String jwt);
    GabiaUserInfoVo getGabiaProfile(HttpServletRequest request) throws Exception;
    UserResponseDto getUserProfileDetail(HttpServletRequest request) throws Exception;
    String destroyToken(HttpServletResponse response);
}
