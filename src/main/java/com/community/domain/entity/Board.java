package com.community.domain.entity;

import com.community.domain.entity.formEntity.AddboardForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Table(name="Board")
@Entity
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="board_id")
    private Long id;

    private String title;

    private String content;

    private LocalDateTime date;

//    @OneToMany(mappedBy="Board", cascade=CascadeType.ALL)
//    private List<AttachedFile> attachedFiles = new ArrayList<>();

    @OneToMany(mappedBy="board")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    //==연관관계 편의 메서드==
//    public void addMember(Member member){
//        this.member = member;
//        member.getBoards().add(this);
//    }
//
//    public void addAttachedFiles(List<AttachedFile> attachedFiles){
//
//    }
    public static Board createBoard(Member member, AddboardForm boardForm) throws IOException {
        Board board = new Board();
        board.setMember(member);
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
//        Board.setAttachedFiles(null);
//        FileStore fileStore = new FileStore();
//        List<AttachedFile> attachedFiles = fileStore.storeFiles(boardForm.getImageFiles());
//        for (AttachedFile attachedFile : attachedFiles) {
//            Board.getAttachedFiles().add(attachedFile);
//            attachedFile.addboard(Board);
//        }
        board.setDate(LocalDateTime.now());
        return board;
    }

    public Board(){}

    public Board editBoard(String title, String content){
        this.setTitle(title);
        this.setContent(content);
        return this;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

//    public Board(String title, String content, List<UploadFile> imageFiles) {
//        this.title = title;
//        this.content = content;
//        this.imageFiles = imageFiles;
//        this.date = LocalDateTime.now();
//    }
//
//    public Board(String title, String content, Member member, List<UploadFile> imageFiles) {
//        this.title = title;
//        this.content = content;
//        this.member = member;
//        this.date = LocalDateTime.now();
//        this.imageFiles = imageFiles;
//    }
}
