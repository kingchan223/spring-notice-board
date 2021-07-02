package com.community.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Writing {
    private Long id;
    private String title;
    private String context;

    public Writing(String title, String context) {
        this.title = title;
        this.context = context;
    }
}
