package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.OrderItemDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.entity.OrderProduct;
import com.hyundai.kosafinal.mapper.userorder.OrderMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper mapper;

    // 주문 목록 조회
    @Override
    public List<OrderedListDTO> getOrderedList(String userEmail) {
        return mapper.getOrderedList(userEmail);
    }

    // 주문 상품 목록 조회
    @Override
    public List<OrderProduct> getOrderItem(String orderedListId) {
        return mapper.getOrderItem(orderedListId);
    }

    // 주문 상세 조회
    @Override
    public OrderedListDTO getOrderDetail(String id) {
        return mapper.getOrderDetail(id);
    }

    // 주문 추가
    @Override
    public String insertOrderedList(OrderedListDTO dto) {
        mapper.insertOrderedList(dto);
        return dto.getId();
    }

    // 주문 아이템 추가
    @Override
    public String insertOrderItem(OrderItemDTO dto) {
        mapper.insertOrderItem(dto);
        return dto.getId();
    }

    @Override
    public int updateOrderDelivery(String id, String zipcode, String address1, String address2, String receiver, String tel, String deliveryMsg) {
        return mapper.updateOrderDelivery(id, zipcode, address1, address2, receiver, tel, deliveryMsg);
    }

    // 주문 상태 변경
    @Override
    public int updateOrderStatus(String id, String userEmail, int status) {
        return mapper.updateOrderStatus(id, userEmail, status);
    }

    // 주문 취소
    @Override
    public int cancelOrder(String id, String userEmail) {
        return mapper.cancelOrder(id, userEmail);
    }

    // 마지막 주문번호 조회
    @Override
    public int getOrderId() {
        return mapper.getOrderId();
    }

    // 재고 체크
    @Override
    public boolean checkStock(String id, String psize, String pcolor, int amount) {
        int stock = mapper.getStock(id, psize, pcolor);

        if(stock < amount) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int updatePoint(String userEmail, int point, int flag) {
        return mapper.updatePoint(userEmail, point, flag);
    }
}
