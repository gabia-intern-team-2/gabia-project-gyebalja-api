package com.gabia.gyebalja.service.jwt;

import java.util.Map;

public interface JwtService {
    <T> String create(T data);
    Map<String, Object> get(String key);
    boolean isUsable(String jwt);
    boolean isRegister(String jwt);
}
