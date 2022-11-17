package com.clrobur.restful.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    private Integer id;
    @Size(min=2, message = "2글자 이상 입력해주세요")
    private String name;
    @Past // 미래 데이터는 사용 불가. 과거 데이터만 사용 가능
    private Date joinDate;

}
