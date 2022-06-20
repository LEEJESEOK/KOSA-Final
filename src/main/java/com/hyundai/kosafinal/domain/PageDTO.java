package com.hyundai.kosafinal.domain;

import com.hyundai.kosafinal.entity.Criteria;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author LEE JESEOK
 */
@Getter
public class PageDTO implements Serializable {
    /**
     * 현재 페이지 그룹의 첫번째 페이지
     * 한 페이지 당 10개 -> endPage - 9
     */
    int startPage;
    /**
     * 현재 페이지 그룹의 마지막 페이지
     */
    int endPage;
    /**
     * 전체 페이지의 마지막 페이지
     */
    int realEnd;
    /**
     * 이전, 다음 페이지 존재 여부
     */
    boolean prev, next;

    Criteria criteria;
    /**
     * 전체 페이지 수
     */
    int total;


    public PageDTO(Criteria criteria, int total) {
        this.criteria = criteria;
        this.total = total;

        // ex) 11~20
        // 10개 단위로 페이지 그룹 생성
        this.endPage = (int) Math.ceil(criteria.getPageNum() * 1.0 / 10.0) * 10;
        this.startPage = this.endPage - 9;
        this.realEnd = (int) Math.ceil(total * 1.0 / criteria.getAmount());

        // 가장 마지막 페이지가 10개 단위보다 부족할 때
        if(this.realEnd < this.endPage)
            this.endPage = this.realEnd;

        this.prev = (this.startPage > 1);
        this.next = (this.endPage < this.realEnd);
    }
}
