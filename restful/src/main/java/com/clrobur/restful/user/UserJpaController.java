package com.clrobur.restful.user;

import com.clrobur.restful.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

// HATEOAS의 메서드 길이를 줄이기 위해 static으로 가져옴
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/jpa")
@RestController
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    // 모든 유저 조회
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    // 개별 유저 조회
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);
        // Optional<> 클래스 : null이 올 수도 있는 값을 감쌀때 사용함
        // .isPresent() 값이 존재하는지 확인
        // .get() 객체 가져옴

        // 조회한 id 값이 없는 경우 예외 객체 던짐
        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] NOT FOUND", id));
        }

        // HATEOAS 추가
        EntityModel<User> resource = EntityModel.of(user.get()); // .get으로 user 객체 가져옴
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers()); // HATEOAS로 링크 연결
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    // 유저 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    // 유저 등록
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        // 저장 메서드 실행
        User savedUser = userRepository.save(user);

        // 받아온 id값 추가해서 uri 수정
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        
        // 수정된 uri값 반환
        return ResponseEntity.created(location).build();
    }
}
