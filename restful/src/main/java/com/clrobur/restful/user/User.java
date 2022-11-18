package com.clrobur.restful.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value={"password", "ssn"}) // JsonIgnore와 동일한 역할을 함
@JsonFilter("UserInfo")
public class User {

    private Integer id;

    @Size(min=2, message = "2글자 이상 입력해주세요")
    private String name;

    @Past // 미래 데이터는 사용 불가. 과거 데이터만 사용 가능
    private Date joinDate;

//    @JsonIgnore // ResponseBody에 해당 필드가 포함되지 않게 함
    private String password;
//    @JsonIgnore
    private String ssn; // 주민번호

}
