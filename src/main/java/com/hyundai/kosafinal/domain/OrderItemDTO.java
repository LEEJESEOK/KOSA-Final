package com.hyundai.kosafinal.domain;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String id;
    private String pid;
    private String psize;
    private String pcolor;
    private int amount;
    private String orderedListId;

}
