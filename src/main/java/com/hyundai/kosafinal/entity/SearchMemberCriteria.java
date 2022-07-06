package com.hyundai.kosafinal.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchMemberCriteria extends SearchCriteria {
    private int selected = -1;

    public SearchMemberCriteria(int pageNum, int amount) {
        super(pageNum, amount);
    }

    public SearchMemberCriteria() {
        super();
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
