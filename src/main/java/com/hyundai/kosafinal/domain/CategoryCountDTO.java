package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CategoryCountDTO {
    int count;
    Map<String, CategoryCountDTO> children;

    public void addCount() {
        ++this.count;
    }
}
