package com.community.service;

import com.community.domain.entity.AttachedFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public List<AttachedFile> storeFiles(List<MultipartFile> imageFiles) throws IOException {
        List<AttachedFile> storeFileResult = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            if(!imageFile.isEmpty()){
                AttachedFile uploadFile = storeFile(imageFile);
                storeFileResult.add(uploadFile);
            }
        }
        return storeFileResult;
    }

    public AttachedFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = UUID.randomUUID().toString()+"."+ extractExt(originalFilename);

        multipartFile.transferTo(new File(fileDir+storeFilename));
        return AttachedFile.createAttachedFile(originalFilename, storeFilename);
    }

    private String extractExt(String originalFilename) {
        int extIndex = originalFilename.lastIndexOf('.');//qweas.png
        return originalFilename.substring(extIndex + 1);
    }
}
