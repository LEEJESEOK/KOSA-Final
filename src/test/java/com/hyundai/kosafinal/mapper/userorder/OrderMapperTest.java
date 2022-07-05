package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.MemberOrderConfirmDTO;
import com.hyundai.kosafinal.domain.OrderItemDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@Log4j2
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderService service;

//    @Test
//    public void getOrderedList() {
//        mapper.getOrderedList("TEST1").forEach(
//                i -> log.info(i)
//        );
//    }
//
//    @Test
//    public void getOrderItem() {
//        mapper.getOrderItem("1").forEach(
//                i -> log.info(i)
//        );
//    }
//
    @Test
    public void insertOrderedList() {
        OrderedListDTO dto = new OrderedListDTO("1", "zip", "addr1", "addr2", "박",
                "01012341234", 10, "TEST1", 45000, new Date(), 0, "배송 메시지", "신용카드");
        log.info(mapper.insertOrderedList(dto));
//        log.info(service.insertOrderedList(dto));
    }
//
//    @Test
//    public void insertOrderItem() {
//        OrderItemDTO dto = new OrderItemDTO("1", "MM2B3WJS085M", "42", "DN", 2, "4");
//        log.info(mapper.insertOrderItem(dto));
//    }
//
//    @Test
//    public void updateOrderStatus() {
//        mapper.updateOrderStatus("4", "TETS1", 1);
//    }
//
//    @Test
//    public void cancleOrder() {
//        mapper.cancleOrder("4", "TEST1");
//    }

    @Test
    public void confirmOrderList() {
        MemberOrderConfirmDTO memberOrderConfirmDTO = mapper.getOrderConfirmByEmail("IL2B3WOT222W", "dbwlgna98");
        System.out.println(memberOrderConfirmDTO);
    }

    @Test
    public void changeUserEmail(){
        String p = "dbwlngs98";
        String s1 = p.substring(0,3);
        String s2 = p.substring(3).replaceAll("[a-z/A-Z/0-9]","*");
        System.out.println(s1+s2);
    }

}
