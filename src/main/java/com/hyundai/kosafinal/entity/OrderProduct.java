package com.hyundai.kosafinal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProduct {
    private String id;
    private String pid;
    private String psize;
    private String pcolor;
    private int amount;
    private String orderedListId;

    // 상품 관련 필드
    private String name;
    private int price;
    private String brand;
    private String image1Uri;

}
