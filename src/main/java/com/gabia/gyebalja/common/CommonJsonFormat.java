package com.gabia.gyebalja.common;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class CommonJsonFormat<T> {
    private T code;
    private T message;
    private T response;

    @Builder
    public CommonJsonFormat(T code, T message, T response){
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public static CommonJsonFormat of(StatusCode e) {
        return CommonJsonFormat.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .response(new ArrayList<>())
                .build();
    }
}