package com.clrobur.restful.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloController {

    @Autowired
    private MessageSource messageSource;


    @GetMapping("/hello")
    public String getHello() {
        return "Hello";
    }

    // Bean 반환 > body에 json타입으로 반환됨
    @GetMapping("/hello-bean")
    public HelloBean beanHello() {
        return new HelloBean("Hello");
    }

    @GetMapping("/hello-bean/{name}")
    public HelloBean beanHello(@PathVariable String name) {
        return new HelloBean(String.format("Hello, %s", name));
    }


    // 다국어 설정
    @GetMapping("/internationalized")
    public String idInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }

}
