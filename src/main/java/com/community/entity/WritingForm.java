package com.community.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

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
