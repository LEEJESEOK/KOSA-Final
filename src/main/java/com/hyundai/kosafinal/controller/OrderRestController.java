package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.AuthMemberDTO;
import com.hyundai.kosafinal.domain.MemberOrderConfirmDTO;
import com.hyundai.kosafinal.domain.OrderItemDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.domain.PageDTO;
import com.hyundai.kosafinal.entity.CartProduct;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.entity.OrderProduct;
import com.hyundai.kosafinal.entity.SearchOrderCriteria;
import com.hyundai.kosafinal.service.OrderService;
import com.hyundai.kosafinal.service.ProductService;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/order")
@PreAuthorize("hasRole('USER')")
public class OrderRestController {

    @Autowired
    private OrderService oService;

    @Autowired
    private ProductService pService;

    // 주문 추가
    @PostMapping("")
    public ResponseEntity<String> insertOrder(@RequestParam Map<String, Object> orderInfo) {
        ResponseEntity<String> entry = null;

        // OrderedList에 값 세팅해서 INSERT
        OrderedListDTO ol = new OrderedListDTO();
        ol.setId(orderInfo.get("id").toString());
        ol.setZipcode(orderInfo.get("zipcode").toString());
        ol.setAddress1(orderInfo.get("address1").toString());
        ol.setAddress2(orderInfo.get("address2").toString());
        ol.setReceiver(orderInfo.get("receiver").toString());
        ol.setTel(orderInfo.get("tel").toString());
        if(!orderInfo.get("usedPoint").equals("")) {
            ol.setUsedPoint(Integer.parseInt(orderInfo.get("usedPoint").toString()));
        } else {
            ol.setUsedPoint(0);
        }

        ol.setUserEmail(orderInfo.get("userEmail").toString());
        ol.setTotalPrice(Integer.parseInt(orderInfo.get("totalPrice").toString()));
        ol.setDeliveryMsg(orderInfo.get("deliveryMsg").toString());
        ol.setPayType(orderInfo.get("paytype").toString());

        String oid = oService.insertOrderedList(ol);
        log.info("INSERT ORDERED LIST : " + ol);

        // OrderItem에 값 세팅해서 INSERT
        int cnt = Integer.parseInt(orderInfo.get("pcount").toString());
        for(int i = 1; i <= cnt; i++) {
            OrderItemDTO oi = new OrderItemDTO();
            oi.setPid(orderInfo.get("pid"+i).toString());
            oi.setPsize(orderInfo.get("psize"+i).toString());
            oi.setPcolor(orderInfo.get("pcolor"+i).toString());
            oi.setAmount(Integer.parseInt(orderInfo.get("amount"+i).toString()));
            oi.setOrderedListId(oid);
            oService.insertOrderItem(oi);
            log.info("INSERT ORDER ITEM : " + oi);
        }

        // 포인트 변경 (사용한 포인트만큼 회원의 포인트 차감)
        if(!orderInfo.get("usedPoint").equals("")) {
            oService.updatePoint(orderInfo.get("userEmail").toString(),
                    Integer.parseInt(orderInfo.get("usedPoint").toString()),
                    1);
        }

        entry = new ResponseEntity<String>(oid, HttpStatus.OK);

        return entry;
    }

    // 주문번호 반환
    @PostMapping("/getId")
    public ResponseEntity<Integer> getOrderId() {
        ResponseEntity<Integer> entry = null;
        int currentId = oService.getOrderId();
        entry = new ResponseEntity<Integer>(currentId, HttpStatus.OK);

        return entry;
    }

    // 해당 주문의 상품들 반환
    @PostMapping("/getOrderItems/{id}")
    public ResponseEntity<List<OrderProduct>> getOrderItems(@PathVariable String id) {
        ResponseEntity<List<OrderProduct>> entry = null;
        List<OrderProduct> list = oService.getOrderItem(id);
        entry = new ResponseEntity<List<OrderProduct>>(list, HttpStatus.OK);
        
        return entry;
    }

    // 주문 배송지 수정
    @PostMapping("/updateDelivery")
    public ResponseEntity<Boolean> updateDelivery(@RequestParam Map<String, String> deliveryInfo) {
        String id = deliveryInfo.get("id");
        String zipcode = deliveryInfo.get("zipcode");
        String address1 = deliveryInfo.get("address1");
        String address2 = deliveryInfo.get("address2");
        String receiver = deliveryInfo.get("receiver");
        String tel = deliveryInfo.get("tel");
        String deliveryMsg = deliveryInfo.get("deliveryMsg");


        log.info(deliveryInfo);
        ResponseEntity<Boolean> entry = null;
        int result = oService.updateOrderDelivery(id, zipcode, address1, address2, receiver, tel, deliveryMsg);
        entry = new ResponseEntity<Boolean>(true, HttpStatus.OK);
        return entry;
    }

    // 주문 상품 재고 체크
    @PostMapping("/checkStock")
    public CartProduct checkStock(@RequestParam Map<String, Object> map) {

        log.info("주문 상품 재고 체크 : " + map);

        CartProduct cp = null;
        int count = Integer.parseInt(map.get("pcount").toString());
        for(int i = 1; i <= count; i++) {
            String id = map.get("id"+i).toString();
            String psize = map.get("psize"+i).toString();
            String pcolor = map.get("pcolor"+i).toString();
            int amount = Integer.parseInt(map.get("amount"+i).toString());

            if(!oService.checkStock(id, psize, pcolor, amount)) {
                cp = new CartProduct();
                cp.setPid(id);
                cp.setPsize(psize);
                cp.setPcolor(pcolor);
                cp.setAmount(amount);
                cp.setName(map.get("name"+i).toString());

                return cp;
            }
        }

        return null;
    }

    // 주문 취소
    @PostMapping("/cancel")
    public ResponseEntity<Boolean> cancelOrder(@RequestParam String id, @AuthenticationPrincipal AuthMemberDTO authentication) {

        ResponseEntity<Boolean> entry = null;
        String userEmail = authentication.getEmail();
        log.info("주문 취소 : " + id + " " + userEmail);
        int result = oService.cancelOrder(id, userEmail);
        entry = new ResponseEntity<>(true, HttpStatus.OK);

        return entry;
    }

    @GetMapping("/confirm")
    public ResponseEntity<MemberOrderConfirmDTO> getProductItem(@RequestParam String productId, @RequestParam String email){

        ResponseEntity<MemberOrderConfirmDTO> entry = null;
        MemberOrderConfirmDTO result = oService.confirmOrderByEmail(productId, email);


        if(result == null || result.getConfirm() != 1){
            MemberOrderConfirmDTO empty = MemberOrderConfirmDTO
              .builder()
              .confirm(0)
              .build();
            return new ResponseEntity<>(empty, HttpStatus.OK);
        }
        System.out.println(result.toString());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(value="/searchOrder", method = RequestMethod.POST)
    public Map<String, Object> searchOrder(@RequestBody SearchOrderCriteria criteria) {
        Map<String, Object> map = new HashMap<>();

        int count = oService.searchOrderCount(criteria);
        List<OrderedListDTO> list = oService.searchOrder(criteria);

        PageDTO pageDTO = new PageDTO(criteria, count);
        log.info("주문 검색: " + list);

        map.put("list", list);
        map.put("page", pageDTO);

        return map;
    }

}
