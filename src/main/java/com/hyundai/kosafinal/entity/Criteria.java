package com.hyundai.kosafinal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Criteria {
    /**
     * 페이지 번호 초기값<br>
     */
    private static final int initPageNum = 1;
    /**
     * 현재 페이지의 아이템 수 초기값<br>
     */
    private static final int initAmount = 12;

    /**
     * 현재 페이지 번호<br>
     */
    private int pageNum;
    /**
     * 현재 페이지의 아이템 수<br>
     */
    private int amount;

    /**
     * 기본 생성자<br>
     * 기본 {@value initPageNum}페이지<br>
     * 페이지 당 기본 {@value initAmount}개<br>
     */
    public Criteria(int pageNum, int amount) {
        if (pageNum < 1)
            this.pageNum = initPageNum;
        else
            this.pageNum = pageNum;

        if (amount < 1) {
            this.amount = initAmount;
        } else {
            this.amount = amount;
        }
    }

    public Criteria() {
        this(initPageNum, initAmount);
    }

    public void setPageNum(int pageNum) {
        if (pageNum < 1)
            this.pageNum = initPageNum;
        else
            this.pageNum = pageNum;
    }

    public void setAmount(int amount) {
        if (amount < 1) {
            this.amount = initAmount;
        } else {
            this.amount = amount;
        }

    }
}
