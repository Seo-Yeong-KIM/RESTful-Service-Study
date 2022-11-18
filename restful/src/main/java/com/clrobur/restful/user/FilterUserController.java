package com.clrobur.restful.user;

import com.clrobur.restful.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 필터 기능을 추가한 컨트롤러
@RestController
@RequestMapping("/filter")
public class FilterUserController {

    private UserDaoService service;

    // 생성자를 통해 의존성 주입
    public FilterUserController(UserDaoService service) {
        this.service = service;
    }

    // -- 메서드 --
    // 전체 유저 목록 조회(필터 추가)
    @GetMapping("/users")
    public MappingJacksonValue allUsers() { // 필터가 추가된 값을 반환하려면 return값 MappingJacksonValue이어야 함

        List<User> users = service.findAll();

        // 필터 생성
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "password"); // 매개변수에 명시된 값만 출력함
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter); // 이때 매개변수로 받는 "UserInfo"는 Entity의 @JsonFilter에 명시된 값과 일치해야함

        // 받아온 유저 정보를 mapping하고 필터 추가함
        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    // 개별 유저 조회(필터 추가)
    @GetMapping("/users/{id}")
    public MappingJacksonValue oneUser(@PathVariable int id) {

        // 유저 정보 받아옴
        User user = service.findOne(id);

        // 조회한 id가 없을 경우
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id)); // Custom 예외 객체 생성
        }

        // 필터 생성
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn"); // 매개변수에 명시된 값만 출력함
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter); // 이때 매개변수로 받는 "UserInfo"는 Entity의 @JsonFilter에 명시된 값과 일치해야함

        // 받아온 유저 정보를 mapping하고 필터 추가함
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

}
