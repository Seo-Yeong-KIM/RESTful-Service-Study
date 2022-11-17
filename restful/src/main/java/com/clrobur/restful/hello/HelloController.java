package com.clrobur.restful.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

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
}
