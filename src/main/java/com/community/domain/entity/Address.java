package com.community.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }
}