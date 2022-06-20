package com.hyundai.kosafinal.domain;

import lombok.Data;

@Data
public class CategoryDTO {
    private String large;
    private String medium;
    private String small;
    private int categoryId;

    public CategoryDTO(String large, String medium, String small) {
        this.large = large;
        this.medium = medium;
        this.small = small;
    }
}
