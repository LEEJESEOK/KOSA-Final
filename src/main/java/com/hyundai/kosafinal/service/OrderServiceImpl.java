package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.OrderItemDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.entity.OrderProduct;
import com.hyundai.kosafinal.mapper.userorder.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper mapper;

    // 주문 목록 조회
    public List<OrderedListDTO> getOrderedList(String userEmail) {
        return mapper.getOrderedList(userEmail);
    }

    // 주문 상품 목록 조회
    public List<OrderProduct> getOrderItem(String orderedListId) {
        return mapper.getOrderItem(orderedListId);
    }

    // 주문 상세 조회
    public OrderedListDTO getOrderDetail(String id) {
        return mapper.getOrderDetail(id);
    }

    // 주문 추가
    public String insertOrderedList(OrderedListDTO dto) {
        mapper.insertOrderedList(dto);
        return dto.getId();
    }

    // 주문 아이템 추가
    public String insertOrderItem(OrderItemDTO dto) {
        mapper.insertOrderItem(dto);
        return dto.getId();
    }

    @Override
    public int updateOrderDelivery(String id, String zipcode, String address1, String address2, String receiver, String tel, String deliveryMsg) {
        return mapper.updateOrderDelivery(id, zipcode, address1, address2, receiver, tel, deliveryMsg);
    }

    // 주문 상태 변경
    public int updateOrderStatus(String id, String userEmail, int status) {
        return mapper.updateOrderStatus(id, userEmail, status);
    }

    // 주문 취소
    public int cancelOrder(String id, String userEmail) {
        return mapper.cancelOrder(id, userEmail);
    }

    // 마지막 주문번호 조회
    public int getOrderId() {
        return mapper.getOrderId();
    }

    @Override
    public int updatePoint(String userEmail, int point, int flag) {
        return mapper.updatePoint(userEmail, point, flag);
    }
}
