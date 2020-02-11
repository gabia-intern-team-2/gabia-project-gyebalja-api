package com.gabia.gyebalja.common.exception;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** 참조 : https://cheese10yun.github.io/spring-guide-exception/ */

@ControllerAdvice
public class CommonExceptionHandler {
    /**
     *  NotExistDataException.class (사용자 정의 Exception)
     *  목적 : (찾고자 하는) 데이터 존재하지 않을 경우 발생
     *  예시 : Repository.findById() 등
     */
    @ExceptionHandler(NotExistDataException.class)
    protected ResponseEntity<CommonJsonFormat> handleNotExistDataException(NotExistDataException e) {
        System.out.println("handleNotExistDataException - " + e);
        final CommonJsonFormat response = CommonJsonFormat.of(StatusCode.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     *  DataIntegrityViolationException.class
     *  목적 : Hibernate 관련 Exception, ConstraintViolationException, PropertyValueException, DataException(잘못된 sql, data) 포함
     *  예시 : Repository.save() 등 (Request DTO 의 누락된 데이터, Null 값 캐치 등)
     * */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<CommonJsonFormat> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        System.out.println("handleDataIntegrityViolationException - " + e);
        final CommonJsonFormat response = CommonJsonFormat.of(StatusCode.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     *  HttpMessageNotReadableException.class (for InvalidFormatException.class)
     *  목적 : @RequestBody 의 잘못된 타입이 들어올 경우 발생 (Enum 포함)
     *  예시 : {
     *      title : "title",
     *      userId : "ID",  <--- Error
     *      type : "ON"     <--- Error
     *  } 등
     * */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<CommonJsonFormat> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        System.out.println("handleHttpMessageNotReadableException - " + e);
        final CommonJsonFormat response = CommonJsonFormat.of(StatusCode.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * HttpRequestMethodNotSupportedException.class
     * 목적 : 지원하지 않는 Method 호출할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<CommonJsonFormat> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        System.out.println("handleHttpRequestMethodNotSupportedException - " + e);
        final CommonJsonFormat response = CommonJsonFormat.of(StatusCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Exception.class
     * 목적 : 주로 발생한 에러 외에 다양한 에러 처리하기 위함
     * */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonJsonFormat> handleException(Exception e) {
        System.out.println("Exception - " + e);
        final CommonJsonFormat response = CommonJsonFormat.of(StatusCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     *  IllegalArgumentException.class
     *  목적 : @RequestBody 유효하지 않은 값 들어올 경우 발생 1
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<CommonJsonFormat> handleIllegalArgumentException(IllegalArgumentException e) {
        System.out.println("handleIllegalArgumentException - " + e);
        final CommonJsonFormat response = CommonJsonFormat.of(StatusCode.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    /**
//     *  MethodArgumentNotValidException.class
//     *  목적 : @RequestBody 유효하지 않은 값 들어올 경우 발생 2
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<CommonJsonFormat> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        System.out.println("handleMethodArgumentNotValidException - " + e);
//        final CommonJsonFormat response = CommonJsonFormat.of(StatusCode.BAD_REQUEST);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    /**
//     * MethodArgumentTypeMismatchException.class
//     * 목적 : enum type 일치하지 않아 binding 못할 경우 발생
//     */
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    protected ResponseEntity<CommonJsonFormat> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
//        System.out.println("handleMethodArgumentTypeMismatchException - " + e);
//        final CommonJsonFormat response = CommonJsonFormat.of(StatusCode.BAD_REQUEST);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
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