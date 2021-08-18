package com.community.domain.entity;

import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.service.FileStore;
import com.community.service.UploadFile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Table(name="board")
@Entity
public class Writing{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="board_id")
    private Long id;

    private String title;

    private String content;

    private LocalDateTime date;

    @OneToMany(mappedBy="writing", cascade=CascadeType.ALL)
    private List<AttachedFile> attachedFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    //==연관관계 편의 메서드==
    public void addMember(Member member){
        this.member = member;
        member.getWritings().add(this);
    }
//
//    public void addAttachedFiles(List<AttachedFile> attachedFiles){
//
//    }

    public static Writing createWriting(Member member, AddWritingForm writingForm) throws IOException {
        Writing writing = new Writing();
        writing.setMember(member);
        writing.setTitle(writingForm.getTitle());
        writing.setContent(writingForm.getContent());
        writing.setAttachedFiles(null);
//        FileStore fileStore = new FileStore();
//        List<AttachedFile> attachedFiles = fileStore.storeFiles(writingForm.getImageFiles());
//        for (AttachedFile attachedFile : attachedFiles) {
//            writing.getAttachedFiles().add(attachedFile);
//            attachedFile.addWriting(writing);
//        }
        writing.setDate(LocalDateTime.now());
        return writing;
    }

    public Writing(){}

//    public Writing(String title, String content, List<UploadFile> imageFiles) {
//        this.title = title;
//        this.content = content;
//        this.imageFiles = imageFiles;
//        this.date = LocalDateTime.now();
//    }
//
//    public Writing(String title, String content, Member member, List<UploadFile> imageFiles) {
//        this.title = title;
//        this.content = content;
//        this.member = member;
//        this.date = LocalDateTime.now();
//        this.imageFiles = imageFiles;
//    }
}
