package com.community.domain.entity.formEntity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class EditBoardForm {

    @NotEmpty(message = "제목을 입려하세요")
    @Length(min=3, max=15)
    private String title;

    @NotEmpty(message="글을 입력하세요")
    private String content;

    public EditBoardForm(){}

    public EditBoardForm(String title, String content){
        this.title = title;
        this.content = content;
    }
}
