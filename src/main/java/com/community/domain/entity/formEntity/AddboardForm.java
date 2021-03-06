package com.community.domain.entity.formEntity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AddboardForm {

    @NotEmpty(message = "제목을 입려하세요")
    @Length(min=3, max=15)
    private String title;

    @NotEmpty(message="글을 입력하세요")
    private String content;

    public AddboardForm(){}

    public AddboardForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
