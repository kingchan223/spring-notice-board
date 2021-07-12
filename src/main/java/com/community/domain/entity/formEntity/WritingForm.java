package com.community.domain.entity.formEntity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WritingForm {

    @NotEmpty(message = "제목을 입려하세요")
    private String title;

    @NotEmpty(message="글을 입력하세요")
    private String content;

    public WritingForm(){}

    public WritingForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
