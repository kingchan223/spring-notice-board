package com.community.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDto {
    private String name;
    private String loginId;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
}
