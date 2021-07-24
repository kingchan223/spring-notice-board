package com.community.domain.entity.formEntity;

import lombok.Data;

@Data
public class EditUserForm {

    private String username;

    private String loginId;

    private String email;

    public EditUserForm(String username, String email, String loginId) {
        this.username = username;
        this.loginId = loginId;
        this.email = email;
    }
}
