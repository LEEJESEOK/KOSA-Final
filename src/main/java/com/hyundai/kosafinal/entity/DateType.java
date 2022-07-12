package com.hyundai.kosafinal.entity;

/**
 * DateFormat 열거형
 * 연, 월, 일, 시, 분
 */
public enum DateType {
    YEAR("yyyy"),
    MONTH("yyyy-MM"),
    DATE("yyyy-MM-dd"),
    HOUR("yyyy-MM-dd HH"),
    MINUTE("yyyy-MM-dd HH:mm");

    final String dateFormatType;

    DateType(String dateFormatType) throws IllegalArgumentException {
        this.dateFormatType = dateFormatType;
    }

    @Override
    public String toString() {
        return dateFormatType;
    }
}
