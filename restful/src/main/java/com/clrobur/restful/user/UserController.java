package com.clrobur.restful.user;

import com.clrobur.restful.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private UserDaoService service;

    // 생성자를 통해 의존성 주입
    public UserController(UserDaoService service) {
        this.service = service;
    }

    // -- 메서드 --
    // 전체 유저 목록 조회
    @GetMapping("/users")
    public List<User> allUsers() {
        return service.findAll();
    }

    // 개별 유저 조회
    @GetMapping("/users/{id}")
    public User oneUser(@PathVariable int id) {
        User user = service.findOne(id);

        // 조회한 id가 없을 경우
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id)); // Custom 예외 객체 생성
        }

        return user; // 값이 null이더라도 응답코드는 200을 받음
    }

    // 유저 정보 저장
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        // 저장메서드 실행(++id값 저장됨)
        User savedUser = service.save(user);

        // 받아온 id값 추가해서 uri 수정
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        // 수정된 uri를 반환
        return ResponseEntity.created(location).build();
    }

    // 유저 정보 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        // 조회한 id가 없는 경우
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }
}
