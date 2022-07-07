package com.hyundai.kosafinal.entity;

import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
public class SearchOrderCriteria extends Criteria {

    private String order_type;
    private String order_keyword;
    private Date start_date;
    private Date end_date;
    private String product_type;
    private String product_keyword;
    private int order_status = -1;

    public SearchOrderCriteria(int pageNum, int amount) {
        super(pageNum, amount);
    }

    public SearchOrderCriteria() {
        super();
    }

    public void setOrderType(String order_type) {
        this.order_type = order_type;
    }

    public void setOrderKeyword(String order_keyword) {
        this.order_keyword = order_keyword;
    }

    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }

    public void setProductType(String product_type) {
        this.product_type = product_type;
    }

    public void setProductKeyword(String product_keyword) {
        this.product_keyword = product_keyword;
    }

    public void setOrderStatus(int order_status) {
        this.order_status = order_status;
    }
}
