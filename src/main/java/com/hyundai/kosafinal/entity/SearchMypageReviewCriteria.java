package com.hyundai.kosafinal.entity;

import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
public class SearchMypageReviewCriteria extends Criteria {

    private String type;
    private String keyword;
    private Date start_date;
    private Date end_date;
    private String category;
    private int status = -1;

    public SearchMypageReviewCriteria(int pageNum, int amount) {
        super(pageNum, amount);
    }

    public SearchMypageReviewCriteria() {
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

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
