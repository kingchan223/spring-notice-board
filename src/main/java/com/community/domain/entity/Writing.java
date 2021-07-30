package com.community.domain.entity;

import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.service.UploadFile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Table(name="board")
@Entity
public class Writing {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="board_id")
    private Long id;

    private String title;

    private String content;

    private LocalDateTime date;

    @Transient
    private List<UploadFile> imageFiles;

    private String imageFileCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    //==연관관계 편의 메서드==
    public void addMember(Member member){
        this.member = member;
        member.getWritings().add(this);
    }

    public static Writing createWriting(Member member, AddWritingForm writingForm){
        Writing writing = new Writing();
        writing.setMember(member);
        writing.setTitle(writingForm.getTitle());
        writing.setContent(writingForm.getContent());
        writing.setDate(LocalDateTime.now());
        return writing;
    }

    public Writing(){}

    public Writing(String title, String content, List<UploadFile> imageFiles) {
        this.title = title;
        this.content = content;
        this.imageFiles = imageFiles;
        this.date = LocalDateTime.now();
    }

    public Writing(String title, String content, Member member, List<UploadFile> imageFiles) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.date = LocalDateTime.now();
        this.imageFiles = imageFiles;
    }
}
