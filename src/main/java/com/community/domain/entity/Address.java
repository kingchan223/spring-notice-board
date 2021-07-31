package com.community.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter @Setter
@AllArgsConstructor
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }
}