package com.community.domain.entity.formEntity;

import lombok.Data;

@Data
public class EditMemberForm {

    private String name;

    private String loginId;

    private String email;

    private String city;

    private String street;

    private String zipcode;

    public EditMemberForm(String name, String email, String loginId, String city, String street, String zipcode) {
        this.name = name;
        this.loginId = loginId;
        this.email = email;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public static EditMemberForm craeteEditUserForm(String name, String email, String loginId, String city, String street, String zipcode){
        EditMemberForm editMemberForm = new EditMemberForm(name, email, loginId, city, street, zipcode);
        return editMemberForm;
    }
}
