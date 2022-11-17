package com.clrobur.restful.exception;

import jdk.jfr.Category;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// 예외 처리 핸들러
@RestController
@ControllerAdvice // 예외 처리를 위한 클래스임을 명시해줌
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest req) {

        // 커스텀 예외 entity (예외 발생 시간, 예외 원인, 상세 내용)
        ExceptionResponse exRes = new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));

        return new ResponseEntity(exRes, HttpStatus.INTERNAL_SERVER_ERROR); // 예외 entity와 서버 에러코드(5xx) 반환
    }

    // 유저 정보가 존재하지 않을 경우
    @ExceptionHandler(UserNotFoundException.class) // UserNot.. 이거 커스텀 예외 클래스
    public final ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest req) {

        // 커스텀 예외 entity (예외 발생 시간, 예외 원인, 상세 내용)
        ExceptionResponse exRes = new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));

        return new ResponseEntity(exRes, HttpStatus.NOT_FOUND);
    }


    // 오버라이딩
    // 유효성 체크에서 오류가 났을 경우
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ExceptionResponse exRes = new ExceptionResponse(new Date(), ex.getMessage(), ex.getBindingResult().toString());
        return new ResponseEntity(exRes, HttpStatus.BAD_REQUEST);
    }
}
