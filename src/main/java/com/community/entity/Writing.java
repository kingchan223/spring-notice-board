package com.community.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name="EMAIL")
    private User user;

    public Writing(){}

    public Writing(String title, String content) {
        this.title = title;
        this.content = content;
        this.date = LocalDate.now().toString();
    }
}
