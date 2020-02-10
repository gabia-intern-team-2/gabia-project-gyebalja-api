package com.gabia.gyebalja.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // --- 4xx Client Error ---
    BAD_REQUEST("400", "BAD_REQUEST"),
    UNAUTHORIZED("401", "Unauthorized"),
    NOT_FOUND("404", "NOT_FOUND"),
    METHOD_NOT_ALLOWED("405", "METHOD_NOT_ALLOWED"),

    // --- 5xx Server Error ---
    INTERNAL_SERVER_ERROR("500", "INTERNAL_SERVER_ERROR");

    // --- 여기에 추가적인 예외 코드 생성 ---
    // Board
    // ...
    // Comment
    // ...

    private final String code;
    private final String message;

    ErrorCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }
}
