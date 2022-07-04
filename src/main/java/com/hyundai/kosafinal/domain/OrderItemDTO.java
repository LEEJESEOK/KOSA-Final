package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDTO {
    private String id;
    private String pid;
    private String psize;
    private String pcolor;
    private int amount;
    private String orderedListId;

}
