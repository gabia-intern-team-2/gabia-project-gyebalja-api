package com.gabia.gyebalja.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotExistEducationException extends RuntimeException {
    public NotExistEducationException(String msg) {
        super(msg);
    }
}
