package com.gabia.gyebalja.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotExistDataException extends RuntimeException {
    public NotExistDataException(String msg) {
        super(msg);
    }
}