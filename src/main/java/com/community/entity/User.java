package com.community.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@AllArgsConstructor
@Table(name="MEMBER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name ="USERNAME")
    private String username;

    @Column(name ="LOGINID")
    private String loginId;

    @Column(name ="EMAIL")
    private String email;

    @Column(name ="PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name ="ROLE")
    private RoleType role;

    @Column(name ="JOINEDDATE")
    private String joinedDate;

    public User(){}

    public User(String username, String email, String password, String loginId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.loginId = loginId;
        this.joinedDate = LocalDateTime.now().toString();
        this.role = RoleType.USER;
    }
}
