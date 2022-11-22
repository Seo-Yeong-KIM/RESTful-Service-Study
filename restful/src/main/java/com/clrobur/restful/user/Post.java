package com.clrobur.restful.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Integer id; // 게시글 고유번호

    private String description; // 게시글 내용

    // Post(다수)와 User(단수)의 관계를 매핑 (N:1)
    @ManyToOne(fetch = FetchType.LAZY) // N:1 관계에서 1에 해당하는 필드 값에 붙힘
    @JsonIgnore // 외부 공개 X
    private User user; // 작성자 정보

}
