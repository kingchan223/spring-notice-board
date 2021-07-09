package com.community.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginUserForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
