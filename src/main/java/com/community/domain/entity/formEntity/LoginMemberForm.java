package com.community.domain.entity.formEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class LoginMemberForm {
    public LoginMemberForm() {
    }

    @NotEmpty(message = "아이디를 입력하세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;

}
