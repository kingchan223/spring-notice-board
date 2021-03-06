package com.community.domain.entity;

import com.community.domain.dto.member.JoinReqDto;
import com.community.domain.entity.formEntity.JoinMemberForm;
import lombok.AllArgsConstructor;
import lombok.Getter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
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

    private String role;

    private LocalDateTime joinedDate;

    @Embedded
    private Address address;

    @OneToMany(mappedBy="member")
    private List<Board> TBoards = new ArrayList<>();

    @OneToMany(mappedBy="member")
    private List<Comment> comments = new ArrayList<>();

    private String refreshToken;

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

    private void setRole(String role) {
        this.role = role;
    }

    private void setJoinedDate(LocalDateTime joinedDate) {
        this.joinedDate = joinedDate;
    }

    private void setboards(List<Board> TBoards) {
        this.TBoards = TBoards;
    }

    private void setAddress(Address address){this.address = address;}

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private void setProvider(String provider) {
        this.provider = provider;
    }

    private void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public Member(){}
    //????????? ???????????? ?????? ???????????????
    public Member(String name){this.name = name;}

    public static Member createMember(JoinMemberForm joinForm, String encPw){
        Member member = new Member();
        member.setName(joinForm.getName());
        member.setEmail(joinForm.getEmail());
        member.setLoginId(joinForm.getLoginId());
        member.setPassword(encPw);
        Address address = new Address(joinForm.getCity(), joinForm.getStreet(), joinForm.getZipcode());
        member.setAddress(address);
        member.setRole("USER");
        member.setJoinedDate(LocalDateTime.now());
        return member;
    }


    public static Member createMember(Long id,String loginId, String email, String name, String role){
        Member member = new Member();
        member.setId(id);
        member.setName(name);
        member.setEmail(email);
        member.setLoginId(loginId);
        member.setRole(role);
        member.setAddress(null);
        member.setPassword(null);
        member.setJoinedDate(null);
        return member;
    }

    public static Member createMemberFromJoinReqDto(JoinReqDto joinReqDto, String encodedPw){
        Member member = new Member();
        member.setName(joinReqDto.getName());
        member.setEmail(joinReqDto.getEmail());
        member.setLoginId(joinReqDto.getLoginId());
        member.setPassword(encodedPw);
        Address address = new Address(joinReqDto.getCity(), joinReqDto.getStreet(), joinReqDto.getZipcode());
        member.setAddress(address);
        member.setRole("USER");
        member.setJoinedDate(LocalDateTime.now());
        return member;
    }

    public Member(String name, String email, String password, String loginId, Long Long) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.loginId = loginId;
        this.joinedDate = LocalDateTime.now();
        this.role ="USER";
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
                                           String password, String role,
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
