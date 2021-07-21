package com.community.domain.entity.formEntity;

import lombok.Data;

@Data
public class editUserForm {

    private String username;

    private String loginId;

    private String email;

    public editUserForm(String username, String email, String loginId) {
        this.username = username;
        this.loginId = loginId;
        this.email = email;
    }
}
