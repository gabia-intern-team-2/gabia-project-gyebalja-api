package com.gabia.gyebalja.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotExistTagException extends RuntimeException {
    public NotExistTagException(String msg) {
        super(msg);
    }
}
