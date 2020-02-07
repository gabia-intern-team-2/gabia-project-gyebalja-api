package com.gabia.gyebalja.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class CommonExceptionHandler {
    /**
     *  IllegalArgumentException.class
     *  목적 : @RequestBody 유효하지 않은 값 들어올 경우 발생 1
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<CommonJsonFormat> handleIllegalArgumentException(IllegalArgumentException e) {
        System.out.println("handleMethodArgumentNotValidException" + e);
        final CommonJsonFormat response = CommonJsonFormat.of(ErrorCode.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     *  MethodArgumentNotValidException.class
     *  목적 : @RequestBody 유효하지 않은 값 들어올 경우 발생 2
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonJsonFormat> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println("handleMethodArgumentNotValidException" + e);
        final CommonJsonFormat response = CommonJsonFormat.of(ErrorCode.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * MethodArgumentTypeMismatchException.class
     * 목적 : enum type 일치하지 않아 binding 못할 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<CommonJsonFormat> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        System.out.println("handleMethodArgumentTypeMismatchException" + e);
        final CommonJsonFormat response = CommonJsonFormat.of(ErrorCode.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * HttpRequestMethodNotSupportedException.class
     * 목적 : 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<CommonJsonFormat> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        System.out.println("handleHttpRequestMethodNotSupportedException" + e);
        final CommonJsonFormat response = CommonJsonFormat.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Exception.class
     * 목적 : 주로 발생한 에러 외에 다양한 에러 처리하기 위함
     * */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonJsonFormat> handleException(Exception e) {
        System.out.println("handleEntityNotFoundException" + e);
        final CommonJsonFormat response = CommonJsonFormat.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    /**
//     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
//     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
//     */
//    @ExceptionHandler(BindException.class)
//    protected ResponseEntity<CommonJsonFormat> handleBindException(BindException e) {
//        System.out.println("handleBindException" + e);
//        final CommonJsonFormat response = CommonJsonFormat.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    /**
//     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
//     */
//    @ExceptionHandler(AccessDeniedException.class)
//    protected ResponseEntity<CommonJsonFormat> handleAccessDeniedException(AccessDeniedException e) {
//        System.out.println("handleAccessDeniedException" + e);
//        final CommonJsonFormat response = CommonJsonFormat.of(ErrorCode.HANDLE_ACCESS_DENIED);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
//    }
//
//    @ExceptionHandler(BusinessException.class)
//    protected ResponseEntity<CommonJsonFormat> handleBusinessException(final BusinessException e) {
//        System.out.println("handleEntityNotFoundException" + e);
//        final ErrorCode errorCode = e.getErrorCode();
//        final CommonJsonFormat response = CommonJsonFormat.of(errorCode);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
//    }
}
