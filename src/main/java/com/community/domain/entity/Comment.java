package com.community.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id  @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="comment_id")
    private Long id;

    private String content;

    private LocalDateTime date;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="writing_id")
    private Writing writing;

    public void addMember(Member member){
        this.member = member;
        member.getComments().add(this);
    }

    public void addWriting(Writing writing){
        this.writing = writing;
        writing.getComments().add(this);
    }
}
