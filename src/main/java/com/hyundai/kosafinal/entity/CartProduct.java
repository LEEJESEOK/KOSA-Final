package com.hyundai.kosafinal.entity;

import com.hyundai.kosafinal.domain.ProductDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CartProduct {

    private int id;
    private String pid;
    private String pcolor;
    private String psize;
    private int amount;
    private String userEmail;

    // Product 관련 필드

    private String name;
    private int price;
    private String brand;
    private String image;
    private int stock;

    private List<ProductDTO> colors;
    private List<ProductDTO> sizes;
}
