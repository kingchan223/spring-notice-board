package com.community.domain.entity;

import com.community.domain.dto.member.MemberDto;
import com.community.domain.entity.formEntity.JoinMemberForm;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@AllArgsConstructor
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="member_id")
    private Long id;

    private String name;

    private String loginId;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private LocalDateTime joinedDate;

    @Embedded
    private Address address;

    @OneToMany(mappedBy="member")
    private List<Writing> writings = new ArrayList<Writing>();

    private String provider;
    private String providerId;

    private void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setRole(RoleType role) {
        this.role = role;
    }

    private void setJoinedDate(LocalDateTime joinedDate) {
        this.joinedDate = joinedDate;
    }

    private void setWritings(List<Writing> writings) {
        this.writings = writings;
    }

    public Member(){}
    //비회원 홈화면을 위한 멤버생성자
    public Member(String name){this.name = name;}

    public static Member createMember(JoinMemberForm joinForm, String encPw){
        Member member = new Member();
        member.setName(joinForm.getName());
        member.setEmail(joinForm.getEmail());
        member.setLoginId(joinForm.getLoginId());
        member.setPassword(encPw);
        Address address = new Address(joinForm.getCity(), joinForm.getStreet(), joinForm.getZipcode());
        member.setAddress(address);
        member.setRole(RoleType.ROLE_USER);
        member.setJoinedDate(LocalDateTime.now());
        return member;
    }

    public static Member createMemberFromDto(MemberDto memberDto, String encodedPw){
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setLoginId(memberDto.getLoginId());
        member.setPassword(encodedPw);
        Address address = new Address(memberDto.getCity(), memberDto.getStreet(), memberDto.getZipcode());
        member.setAddress(address);
        member.setRole(RoleType.ROLE_USER);
        member.setJoinedDate(LocalDateTime.now());
        return member;
    }

    public Member(String name, String email, String password, String loginId, Long Long) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.loginId = loginId;
        this.joinedDate = LocalDateTime.now();
        this.role = RoleType.ROLE_USER;
        this.id = id;
    }

    public Member changeMember(String name, String email, String loginId, String city, String street, String zipcode){
        this.name = name;
        this.email = email;
        this.loginId = loginId;
        this.address.setCity(city);
        this.address.setStreet(street);
        this.address.setZipcode(zipcode);
        return this;
    }

    public static Member createOAuthMember(String name,
                                           String loginId, String email,
                                           String password, RoleType role,
                                           String provider, String providerId){
        Member member = new Member();
        member.setName(name);
        member.setLoginId(loginId);
        member.setEmail(email);
        member.setPassword(password);
        member.setRole(role);
        member.setProvider(provider);
        member.setProviderId(providerId);
        member.setAddress(Address.createAddress("empty", "empty", "empty"));
        return member;
    }
}
