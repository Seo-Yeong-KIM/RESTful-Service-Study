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

    // 유저 repository
    @Autowired
    private UserRepository userRepository;
    // 게시글 repository
    @Autowired
    private PostRepository postRepository;


    // 1. 모든 유저 조회
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    // 2. 개별 유저 조회
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

    // 3. 유저 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    // 4. 유저 등록
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

    // 5. 개별 유저가 등록한 게시글 조회
    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);

        // 조회한 id 값이 없는 경우 예외 객체 던짐
        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s]'S POSTS NOT FOUND", id));
        }

        return user.get().getPosts();
    }

    // 6. 게시글 등록
    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Integer id, @RequestBody Post post) { // id는 단일 값으로, post 값은 객체 형태로 받아옴

        // 받아온 id 값이 null 일 수도 있으니 검증함
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] NOT FOUND", id));
        }

        // id 값으로 user 정보 가져와서 세팅 후 저장
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
