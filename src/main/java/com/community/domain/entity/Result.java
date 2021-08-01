package com.community.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Result {
    private ArrayList<Documents> documents;
    private Meta meta;
}
