package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDTO {

    private int id;
    private String pid;
    private String pcolor;
    private String psize;
    private int amount;
    private String userEmail;

}
