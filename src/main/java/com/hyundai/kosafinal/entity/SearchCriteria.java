package com.hyundai.kosafinal.entity;

import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
public class SearchCriteria extends Criteria {

    /**
     * 검색 기준
     */
    private String type;

    /**
     * 검색어
     */
    private String keyword;

    private Date start_date;
    private Date end_date;
    public SearchCriteria(int pageNum, int amount) {
        super(pageNum, amount);
    }

    public SearchCriteria() {
        super();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }
}
