package com.community.domain.entity;

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

    public static Member createMember(JoinMemberForm joinForm){
        Member member = new Member();
        member.setName(joinForm.getName());
        member.setEmail(joinForm.getEmail());
        member.setLoginId(joinForm.getLoginId());
        member.setPassword(joinForm.getPassword());
        Address address = new Address(joinForm.getCity(), joinForm.getStreet(), joinForm.getZipcode());
        member.setAddress(address);
        member.setRole(RoleType.USER);
        member.setJoinedDate(LocalDateTime.now());
        return member;
    }

    public Member(String name, String email, String password, String loginId, Long Long) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.loginId = loginId;
        this.joinedDate = LocalDateTime.now();
        this.role = RoleType.USER;
        this.id = id;
    }

    public Member changeUser(String name, String email, String loginId){
        this.name = name;
        this.email = email;
        this.loginId = loginId;
        return this;
    }


}
