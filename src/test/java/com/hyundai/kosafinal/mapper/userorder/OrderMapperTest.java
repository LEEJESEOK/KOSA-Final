package com.hyundai.kosafinal.mapper.userorder;

import com.hyundai.kosafinal.domain.CategoryCountDTO;
import com.hyundai.kosafinal.domain.MemberOrderConfirmDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.entity.OrderProduct;
import com.hyundai.kosafinal.entity.SearchOrderCriteria;
import com.hyundai.kosafinal.mapper.product.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.*;


@Log4j2
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper mapper;

    @Autowired
    ProductMapper productMapper;

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
//    @Test
//    public void insertOrderedList() {
//        OrderedListDTO dto = new OrderedListDTO("1", "zip", "addr1", "addr2", "박",
//                "01012341234", 10, "TEST1", 45000, new Date(), 0, "배송 메시지", "신용카드", "");
//        log.info(mapper.insertOrderedList(dto));
////        log.info(service.insertOrderedList(dto));
//    }
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
    public void changeUserEmail() {
        String p = "dbwlngs98";
        String s1 = p.substring(0, 3);
        String s2 = p.substring(3).replaceAll("[a-z/A-Z/0-9]", "*");
        System.out.println(s1 + s2);
    }

    @Test
    public void searchOrder() {
        SearchOrderCriteria soc = new SearchOrderCriteria(1, 10);
        soc.setOrderType("receiver");
        soc.setOrderKeyword("박");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date start = Date.valueOf("2022-06-30");
        Date end = Date.valueOf("2022-07-04");
        soc.setStartDate(start);
        soc.setEndDate(end);

        soc.setProductType("name");
        soc.setProductKeyword("백 그");
        List<OrderedListDTO> list = mapper.searchOrder(soc);
        log.info(list);

        soc.setProductType("id");
        soc.setProductKeyword("MM2B3WJS085M");
        soc.setOrderStatus(0);
        List<OrderedListDTO> list2 = mapper.searchOrder(soc);
        log.info(list2);

    }

    @Test
    void selectOrderBrandCountByMemberId() {
        String memberId = "jadyn";
        List<OrderedListDTO> orderedList = mapper.getOrderedList(memberId);

        // 회원의 구매 목록 리스트
        List<String> orderedIdList = new ArrayList<>();
        for (OrderedListDTO dto : orderedList) {
            orderedIdList.add(dto.getId());
        }

        // 구매 목록의 상품 리스트
        List<OrderProduct> orderedProductList = new ArrayList<>();
        for (String orderId : orderedIdList) {
            orderedProductList.addAll(mapper.getOrderItem(orderId));
        }

        // 상품 리스트의 브랜드 개수
        Map<String, Integer> brandCountMap = new HashMap<>();
        for (OrderProduct orderProduct : orderedProductList) {
            String brand = orderProduct.getBrand();
            brandCountMap.put(brand, brandCountMap.getOrDefault(brand, 0) + 1);
        }

        System.out.println(brandCountMap);
    }

    @Test
    void selectOrderedCategoryCountByMemberId() {
        String memberId = "jadyn";
        List<OrderedListDTO> orderedList = mapper.getOrderedList(memberId);

        // 회원의 구매 목록 리스트
        List<String> orderedIdList = new ArrayList<>();
        for (OrderedListDTO dto : orderedList) {
            orderedIdList.add(dto.getId());
        }

        // 구매 목록의 상품 리스트
        List<OrderProduct> orderedProductList = new ArrayList<>();
        for (String orderId : orderedIdList) {
            orderedProductList.addAll(mapper.getOrderItem(orderId));
        }
        // 상품 리스트의 카테고리 개수
        Map<String, CategoryCountDTO> categoryCountMap = new HashMap<>();

        for (OrderProduct orderProduct : orderedProductList) {
            System.out.println(orderProduct);
            // 상품의 카테고리 정보
            List<ProductDTO> productDTOList = productMapper.selectProductDetailById(orderProduct.getPid());

            String large = productDTOList.get(0).getCategoryLarge();
            String medium = productDTOList.get(0).getCategoryMedium();
            String small = productDTOList.get(0).getCategorySmall();

            CategoryCountDTO largeCount = categoryCountMap.getOrDefault(large,
                    new CategoryCountDTO(0, new HashMap<>()));
            largeCount.addCount();

            CategoryCountDTO mediumCount = largeCount.getChildren().getOrDefault(medium,
                    new CategoryCountDTO(0, new HashMap<>()));
            mediumCount.addCount();

            CategoryCountDTO smallCount = mediumCount.getChildren().getOrDefault(small,
                    new CategoryCountDTO(0, null));
            smallCount.addCount();

            mediumCount.getChildren().put(small, smallCount);
            largeCount.getChildren().put(medium, mediumCount);
            categoryCountMap.put(large, largeCount);

        }

        System.out.println("small count");
        for (String largeKey : categoryCountMap.keySet()) {
            CategoryCountDTO large = categoryCountMap.get(largeKey);
            for (String mediumKey : large.getChildren().keySet()) {
                CategoryCountDTO medium = large.getChildren().get(mediumKey);
                for (String smallKey : medium.getChildren().keySet()) {
                    System.out.println(smallKey + " : " + medium.getChildren().get(smallKey).getCount());
                }
            }
        }

        System.out.println("medium count");
        for (String largeKey : categoryCountMap.keySet()) {
            CategoryCountDTO large = categoryCountMap.get(largeKey);
            for (String mediumKey : large.getChildren().keySet()) {
                System.out.println(mediumKey + " : " + large.getChildren().get(mediumKey).getCount());
            }
        }

        System.out.println("large count");
        for (String largeKey : categoryCountMap.keySet()) {
            System.out.println(largeKey + " : " + categoryCountMap.get(largeKey).getCount());
        }
    }

    @Test
    void selectOrderOnDashboard() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.DATE, -1);

        List<HashMap<String, Object>> selectSalesResult = mapper.selectOrderCount(calendar.getTime());

        for (HashMap<String, Object> map : selectSalesResult) {
            System.out.println(map);
        }

        List<HashMap<String, Object>> selectRevenueResult = mapper.selectOrderRevenue(calendar.getTime());

        for (HashMap<String, Object> map : selectRevenueResult) {
            System.out.println(map);
        }

        List<HashMap<String, Object>> selectCustomerResult = mapper.selectOrderCustomer(calendar.getTime());

        for (HashMap<String, Object> map : selectCustomerResult) {
            System.out.println(map);
        }
    }
}
