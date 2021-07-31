package com.community.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Table(name="attached_file")
@Entity
public class AttachedFile {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="attached_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="writing_id")
    private Writing writing;

    private String storeFilename;
    private String uploadFilename;

    private void setId(Long id) {
        this.id = id;
    }
    private void setWriting(Writing writing) {
        this.writing = writing;
    }
    private void setStoreFilename(String storeFilename) {
        this.storeFilename = storeFilename;
    }
    private void setUploadFilename(String uploadFilename) {
        this.uploadFilename = uploadFilename;
    }

    public Long getId() {
        return id;
    }
    public Writing getWriting() {
        return writing;
    }
    public String getStoreFilename() {
        return storeFilename;
    }
    public String getUploadFilename() {
        return uploadFilename;
    }

    public static AttachedFile createAttachedFile(String originalFilename, String storeFilename){
        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setStoreFilename(storeFilename);
        attachedFile.setUploadFilename(originalFilename);

        return attachedFile;
    }
}
