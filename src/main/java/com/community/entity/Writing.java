package com.community.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Writing {
    private Long id;
    private String title;
    private String context;
    private String date;

    public Writing(String title, String context) {
        this.title = title;
        this.context = context;
        this.date = LocalDateTime.now().toString();
    }
}
