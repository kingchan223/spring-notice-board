package com.community.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

@Data
public class JoinUserForm {

    @NotEmpty(message="아이디는 필수 입니다.")
    private String loginId;

    @NotEmpty(message="비밀번호는 필수 입니다.")
    private String password;

    @NotEmpty(message="이름은 필수 입니다.")
    private String username;

    @NotEmpty(message="이메일은 필수 입니다.")
    private String email;
}
