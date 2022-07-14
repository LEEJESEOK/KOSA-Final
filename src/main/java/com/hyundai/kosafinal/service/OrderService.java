package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.MemberOrderConfirmDTO;
import com.hyundai.kosafinal.domain.OrderItemDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.domain.CategoryCountDTO;
import com.hyundai.kosafinal.entity.DateType;
import com.hyundai.kosafinal.entity.OrderProduct;
import com.hyundai.kosafinal.entity.SearchOrderCriteria;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {

    // 주문 목록 조회
    public List<OrderedListDTO> getOrderedList(String userEmail);

    // 주문 상품 목록 조회
    public List<OrderProduct> getOrderItem(String orderedListId);

    // 주문 상세 조회
    public OrderedListDTO getOrderDetail(String id);

    // 주문 추가
    public String insertOrderedList(OrderedListDTO dto);

    // 주문 아이템 추가
    public String insertOrderItem(OrderItemDTO dto);

    // 배송정보 변경
    public int updateOrderDelivery(String id, String zipcode, String address1, String address2, String receiver, String tel, String deliveryMsg);

    // 주문 취소
    public int cancelOrder(String id);

    // 마지막 주문번호 조회
    public int getOrderId();

    // 재고 체크
    public boolean checkStock(String id, String psize, String pcolor, int amount);

    // 포인트 변경
    public int updatePoint(String userEmail, int point, int flag);

    public MemberOrderConfirmDTO confirmOrderByEmail(String productId, String email);

    // 백오피스 관련 주문 검색 결과 수
    public int searchOrderCount(SearchOrderCriteria criteria);

    // 백오피스 관련 주문 검색 및 페이징 조회
    public List<OrderedListDTO> searchOrder(SearchOrderCriteria criteria);

    // 백오피스 관련 기간별 전체 주문 횟수
    // 날짜 : 횟수
    Map<String, Integer> getSalesCount(Date start, DateType dateType);

    // 백오피스 관련 기간별 전체 주문 금액
    // 날짜 : 금액
    Map<String, Integer> getRevenue(Date start, DateType dateType);

    // 백오피스 관련 기간별 전체 주문자 수
    // 날짜 : 주문자 수
    Map<String, Integer> getCustomerCount(Date start, DateType dateType);

    // 백오피스 관련 회원의 단위기간별 구매 금액
    Map<String, Integer> getOrderedDatePriceByMemberId(String memberId, DateType dateType);

    // 백오피스 관련 사용자 구매기록의 브랜드별 횟수
    Map<String, Integer> getOrderedBrandCountByMemberId(String memberId);

    // 백오피스 관련 사용자 구매기록의 카테고리별 횟수
    Map<String, CategoryCountDTO> getOrderedCategoryCountByMemberId(String memberId);
}
