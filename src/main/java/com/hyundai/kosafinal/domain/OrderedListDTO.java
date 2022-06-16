package com.hyundai.kosafinal.domain;

import lombok.Data;

import java.util.Date;

@Data
public class OrderedListDTO {
    private String id;
    private String zipcode;
    private String address1;
    private String address2;
    private String receiver;
    private String tel;
    private int usedPoint;
    private String userEmail;
    private int totalPrice;
    private Date date;
}
