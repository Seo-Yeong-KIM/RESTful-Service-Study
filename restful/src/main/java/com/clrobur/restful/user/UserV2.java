package com.clrobur.restful.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("UserInfoV2")
public class UserV2 extends User { // User클래스를 상속받음

    private String grade;

}
