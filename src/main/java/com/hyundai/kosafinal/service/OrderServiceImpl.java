package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.MemberOrderConfirmDTO;
import com.hyundai.kosafinal.domain.OrderItemDTO;
import com.hyundai.kosafinal.domain.OrderedListDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.CategoryCountDTO;
import com.hyundai.kosafinal.entity.DateType;
import com.hyundai.kosafinal.entity.OrderProduct;
import com.hyundai.kosafinal.entity.SearchOrderCriteria;
import com.hyundai.kosafinal.mapper.product.ProductMapper;
import com.hyundai.kosafinal.mapper.userorder.OrderMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Log4j2
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper mapper;

    @Autowired
    ProductMapper productMapper;

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

        if (stock < amount) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int updatePoint(String userEmail, int point, int flag) {
        return mapper.updatePoint(userEmail, point, flag);
    }

    @Override
    public MemberOrderConfirmDTO confirmOrderByEmail(String productId, String email) {
        return mapper.getOrderConfirmByEmail(productId, email);
    }

    // 백오피스 관련 주문 검색 결과 수
    @Override
    public int searchOrderCount(SearchOrderCriteria criteria) {
        return mapper.searchOrderCount(criteria);
    }

    // 백오피스 관련 주문 검색 및 페이징 조회
    @Override
    public List<OrderedListDTO> searchOrder(SearchOrderCriteria criteria) {
        return mapper.searchOrder(criteria);
    }

    // 회원의 단위기간별 구매 금액
    @Override
    public Map<String, Integer> getOrderedDatePriceByMemberId(String memberId, DateType dateType) {
        // 회원의 구매한 상품 리스트
        List<OrderedListDTO> orderedList = mapper.getOrderedList(memberId);

        System.out.println("count : " + orderedList.size());

        Map<String, Integer> resultMap = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateType.toString());

        // 구매한 상품의 날짜당 가격 합계
        for (OrderedListDTO order : orderedList) {
            System.out.println("order : " + order);

            Date orderDate = order.getDate();
            resultMap.put(simpleDateFormat.format(orderDate),
                    resultMap.getOrDefault(simpleDateFormat.format(orderDate), 0) + order.getTotalPrice());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        switch (dateType) {
            // 최근 5년
            case YEAR:
                for (int i = 0; i < 5; ++i) {
                    calendar.add(Calendar.YEAR, -1);
                    String dateKey = simpleDateFormat.format(calendar.getTime());
                    resultMap.put(dateKey, resultMap.getOrDefault(dateKey, 0));
                }
                break;
            // 최근 12개월
            case MONTH:
                for (int i = 0; i < 12; ++i) {
                    calendar.add(Calendar.MONTH, -1);
                    String dateKey = simpleDateFormat.format(calendar.getTime());
                    resultMap.put(dateKey, resultMap.getOrDefault(dateKey, 0));
                }
                break;
            // 최근 7일
            case DAY:
                for (int i = 0; i <= 7; i++) {
                    calendar.add(Calendar.DATE, -1);
                    String dateKey = simpleDateFormat.format(calendar.getTime());
                    resultMap.put(dateKey, resultMap.getOrDefault(dateKey, 0));
                }
                break;
            // 최근 24시간
            case HOUR:
                for (int i = 0; i < 24; i++) {
                    calendar.add(Calendar.HOUR, -1);
                    String dateKey = simpleDateFormat.format(calendar.getTime());
                    resultMap.put(dateKey, resultMap.getOrDefault(dateKey, 0));
                }
                break;
        }


        return resultMap;
    }

    // 회원의 브랜드별 구매 횟수
    @Override
    public Map<String, Integer> getOrderedBrandCountByMemberId(String memberId) {
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

        return brandCountMap;
    }

    // 회원의 카테고리별 구매 횟수
    @Override
    public Map<String, CategoryCountDTO> getOrderedCategoryCountByMemberId(String memberId) {
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

            // 카테고리 수준별 횟수 계산
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

        return categoryCountMap;
    }
}
