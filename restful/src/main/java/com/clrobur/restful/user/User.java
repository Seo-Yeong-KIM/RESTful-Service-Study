package com.clrobur.restful.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(value={"password", "ssn"}) // JsonIgnore와 동일한 역할을 함
//@JsonFilter("UserInfo") // 필터 사용하려면 이 주석 해제해야함
@ApiModel(description = "사용자 상세 정보를 위한 도메인(info) 객체")
public class User {

    private Integer id;

    @Size(min=2, message = "2글자 이상 입력해주세요")
    @ApiModelProperty(notes = "사용자 이름을 입력하세요")
    private String name;

    @Past // 미래 데이터는 사용 불가. 과거 데이터만 사용 가능
    @ApiModelProperty(notes = "등록일을 입력하세요")
    private Date joinDate;

//    @JsonIgnore // ResponseBody에 해당 필드가 포함되지 않게 함
    @ApiModelProperty(notes = "비밀번호를 입력하세요")
    private String password;
//    @JsonIgnore
    @ApiModelProperty(notes = "주민번호를 입력하세요")
    private String ssn; // 주민번호

}
