package com.hyundai.kosafinal.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GradeDTO {
    private int id;
    private String name;
    private int min;
    private int max;
}
