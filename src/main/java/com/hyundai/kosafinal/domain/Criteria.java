package com.hyundai.kosafinal.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Criteria {
    private static int initPage = 1;
    private static int initPageAmount = 10;

    /**
     * 현재 페이지 번호
     */
    @Getter
    private int page;
    /**
     * 페이지 목록에서 보여줄 페이지 수
     */
    private int pageAmount;

    /**
     * 기본 생성자
     * 기본 {initPage}페이지, {initPageAmount}개
     */
    public Criteria() {
        this(1, 10);
    }
}
