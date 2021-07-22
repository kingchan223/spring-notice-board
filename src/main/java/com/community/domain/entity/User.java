package com.community.domain.entity;

import com.community.domain.entity.formEntity.editUserForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
//@Entity
@AllArgsConstructor
//@Table(name="USER")
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name="USER_ID")
    private Long id;

//    @Column(name ="USERNAME")
    private String username;

//    @Column(name ="LOGINID")
    private String loginId;

//    @Column(name ="EMAIL")
    private String email;

    //@Column(name ="PASSWORD")
    private String password;

//    @Enumerated(EnumType.STRING)
//    @Column(name ="ROLE")
    private RoleType role;

//    @Column(name ="JOINEDDATE")
    private String joinedDate;

//    @OneToMany(mappedBy="user")
    private List<Writing> writings = new ArrayList<Writing>();

    private void setId(Long id) {
        this.id = id;
    }

    private void setUsername(String username) {
        this.username = username;
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

    private void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    private void setWritings(List<Writing> writings) {
        this.writings = writings;
    }

    public User(){}

    public User(String username, String email, String password, String loginId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.loginId = loginId;
        this.joinedDate = LocalDateTime.now().toString();
        this.role = RoleType.USER;
    }

    public User changeUser(editUserForm newUser){
        this.username = newUser.getUsername();
        this.email = newUser.getEmail();
        this.loginId = newUser.getLoginId();

        return this;
    }
}
