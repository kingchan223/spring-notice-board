package com.community.domain.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Result {
    private ArrayList<Documents> documents;
    private Meta meta;
}
