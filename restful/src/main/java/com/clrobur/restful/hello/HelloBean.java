package com.clrobur.restful.hello;

import lombok.*;

@Data
@AllArgsConstructor // 모든 필드를 갖는 생성자
@NoArgsConstructor // 기본 생성자(필드값 X)
public class HelloBean {
    private String message;
}
