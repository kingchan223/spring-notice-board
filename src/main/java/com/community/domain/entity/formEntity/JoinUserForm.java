package com.community.domain.entity.formEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
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
