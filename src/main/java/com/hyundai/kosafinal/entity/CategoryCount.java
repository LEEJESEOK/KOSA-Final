package com.hyundai.kosafinal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CategoryCount {
    int count;
    Map<String, CategoryCount> children;

    public void addCount() {
        ++this.count;
    }
}
