package com.community.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Documents {
    private String title;
    private String contents;
    private String url;
    private String datetime;
}
