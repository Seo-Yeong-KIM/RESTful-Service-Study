package com.clrobur.restful.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 모든 컨트롤러에서 통용되는 예외 entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private Date timestamp; // 예외 발생 시간
    private String message; // 예외 원인
    private String details; // 상세 내용

}
