package com.community.domain.entity;

import com.community.service.UploadFile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
//@Table(name="BOARD")
//@Entity
public class Writing {
//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name="BOARD_ID")
    private Long id;

//    @Column(name="TITLE")
    private String title;

//    @Column(name="CONTENT")
    private String content;

//    @Column(name="DATE")
    private String date;

    private List<UploadFile> imageFiles;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="USER_ID")
    private User user;

    public Writing(){}

    public Writing(String title, String content, List<UploadFile> imageFiles) {
        this.title = title;
        this.content = content;
        this.imageFiles = imageFiles;
        this.date = LocalDate.now().toString();
    }

    public Writing(String title, String content, User user, List<UploadFile> imageFiles) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.date = LocalDate.now().toString();
        this.imageFiles = imageFiles;
    }
}
