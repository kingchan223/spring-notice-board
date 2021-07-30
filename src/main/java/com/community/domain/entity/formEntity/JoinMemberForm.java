package com.community.domain.entity.formEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class JoinMemberForm {

    @NotEmpty(message="아이디는 필수 입니다.")
    private String loginId;

    @NotEmpty(message="비밀번호는 필수 입니다.")
    private String password;

    @NotEmpty(message="이름은 필수 입니다.")
    private String name;

    @NotEmpty(message="이메일은 필수 입니다.")
    private String email;

    @NotEmpty(message="도시를 입력하세요.")
    private String city;

    @NotEmpty(message="구, 군을 입력화세요.")
    private String street;

    @NotEmpty(message="우편번호를 입력하세요.")
    private String zipcode;
}
