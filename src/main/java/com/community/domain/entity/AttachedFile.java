package com.community.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


//@Table(name="attached_file")
//@Entity
public class AttachedFile {

//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name="attached_file_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name ="board_id")
//    private Board Board;
//
//    private String storeFilename;
//    private String uploadFilename;
//
//    private void setId(Long id) {
//        this.id = id;
//    }
//    private void setboard(Board Board) {
//        this.Board = Board;
//    }
//    private void setStoreFilename(String storeFilename) {
//        this.storeFilename = storeFilename;
//    }
//    private void setUploadFilename(String uploadFilename) {
//        this.uploadFilename = uploadFilename;
//    }
//
//    public Long getId() {
//        return id;
//    }
//    public Board getboard() {
//        return Board;
//    }
//    public String getStoreFilename() {
//        return storeFilename;
//    }
//    public String getUploadFilename() {
//        return uploadFilename;
//    }
//
//    public static AttachedFile createAttachedFile(String originalFilename, String storeFilename){
//        AttachedFile attachedFile = new AttachedFile();
//        attachedFile.setStoreFilename(storeFilename);
//        attachedFile.setUploadFilename(originalFilename);
//
//        return attachedFile;
//    }
//    //연관관계 편의 메소드
//    public void addboard(Board Board){
//        this.setboard(Board);
//    }
}
