package com.clrobur.restful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Custom 예외 클래스
// 유저정보가 존재하지 않을 경우 발생
@ResponseStatus(HttpStatus.NOT_FOUND) // 404 코드로 전달
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
