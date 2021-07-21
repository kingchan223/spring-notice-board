package com.community.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Table(name="BOARD")
@Entity
public class Writing {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name="BOARD_ID")
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="CONTENT")
    private String content;

    @Column(name="DATE")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    public Writing(){}

    public Writing(String title, String content) {
        this.title = title;
        this.content = content;
        this.date = LocalDate.now().toString();
    }

    public Writing(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.date = LocalDate.now().toString();
    }
}
