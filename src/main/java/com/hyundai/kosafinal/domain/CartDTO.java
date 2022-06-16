package com.hyundai.kosafinal.domain;

import lombok.Data;

@Data
public class CartDTO {
    private String pid;
    private String pcolor;
    private String psize;
    private int amount;
    private String userEmail;
}
