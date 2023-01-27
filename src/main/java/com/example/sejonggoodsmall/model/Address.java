package com.example.sejonggoodsmall.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String mainAddress;
    private String detailAddress;
    private String zipcode;

    protected Address() {
    }

    public Address(String mainAddress, String detailAddress, String zipcode) {
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }
}
