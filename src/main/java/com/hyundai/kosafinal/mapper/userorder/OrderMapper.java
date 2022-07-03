package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.OrderItemDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.entity.OrderProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    // 주문 목록 조회
    public List<OrderedListDTO> getOrderedList(String userEmail);

    // 주문 상품 목록 조회
    public List<OrderProduct> getOrderItem(String orderedListId);

    // 주문 상세 조회
    public OrderedListDTO getOrderDetail(String id);

    // 주문 추가
    public int insertOrderedList(OrderedListDTO dto);


    // 주문 아이템 추가
    public int insertOrderItem(OrderItemDTO dto);

    // 배송정보 변경
    public int updateOrderDelivery(String id, String zipcode, String address1, String address2, String receiver, String tel, String deliveryMsg);

    // 주문 상태 변경
    public int updateOrderStatus(String id, String userEmail, int status);

    // 주문 취소
    public int cancelOrder(String id, String userEmail);

    // 마지막 주문번호 조회
    public int getOrderId();

    // 포인트 변경
    public int updatePoint(String userEmail, int point, int flag);

}
