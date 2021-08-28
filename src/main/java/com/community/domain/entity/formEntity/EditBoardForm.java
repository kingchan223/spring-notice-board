package com.community.domain.entity.formEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
public class EditBoardForm {

    private Long id;

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
