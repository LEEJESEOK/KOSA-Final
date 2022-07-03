package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
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
    private int status;
    private String deliveryMsg;
    private String payType;
}
