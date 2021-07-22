package com.community.service;

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

    public List<UploadFile> storeFiles(List<MultipartFile> imageFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            if(!imageFile.isEmpty()){
                UploadFile uploadFile = storeFile(imageFile);
                storeFileResult.add(uploadFile);
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = UUID.randomUUID().toString()+"."+ extractExt(originalFilename);

        multipartFile.transferTo(new File(fileDir+storeFilename));
        return new UploadFile(originalFilename, storeFilename);
    }

    private String extractExt(String originalFilename) {
        int extIndex = originalFilename.lastIndexOf('.');//qweas.png
        return originalFilename.substring(extIndex + 1);
    }
}
