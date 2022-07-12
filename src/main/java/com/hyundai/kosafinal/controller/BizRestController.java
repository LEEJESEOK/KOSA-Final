package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.Member2DTO;
import com.hyundai.kosafinal.domain.PageDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.SelectProductCriteria;
import com.hyundai.kosafinal.entity.DateType;
import com.hyundai.kosafinal.service.MemberService;
import com.hyundai.kosafinal.service.OrderService;
import com.hyundai.kosafinal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author LEE JESEOK
 * @author YOU JIHOON
 */
@RestController
@RequestMapping("/api/biz")
public class BizRestController {

    @Autowired
    ProductService productService;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderService orderService;

    @PostMapping("/product/register")
    public ResponseEntity<Integer> productRegister(MultipartHttpServletRequest request) {
        ProductDTO productDTO = ProductDTO.builder()
                .id(request.getParameter("id"))
                .size(request.getParameter("size"))
                .colorId(request.getParameter("colorId"))
                .name(request.getParameter("name"))
                .brand(request.getParameter("brand"))
                .categoryLarge(request.getParameter("categoryLarge"))
                .categoryMedium(request.getParameter("categoryMedium"))
                .categorySmall(request.getParameter("categorySmall"))
                .detail(request.getParameter("detail"))
                .price(Integer.parseInt(request.getParameter("price")))
                .stockAmount(request.getParameter("stockAmount"))
                .build();

        List<MultipartFile> files = new ArrayList<>();
        if (!request.getFiles("image1Uri").isEmpty() && request.getFiles("image1Uri").get(0).getSize() != 0) {
            files.add(request.getFile("image1Uri"));
        }
        if (!request.getFiles("image2Uri").isEmpty() && request.getFiles("image2Uri").get(0).getSize() != 0) {
            files.add(request.getFile("image2Uri"));
        }
        if (!request.getFiles("image3Uri").isEmpty() && request.getFiles("image3Uri").get(0).getSize() != 0) {
            files.add(request.getFile("image3Uri"));
        }

        String colorChip = productService.getColorChip(productDTO.getColorId());
        productDTO.setColorChipUri(colorChip);

        int result = productService.saveProductInfo(productDTO, files);
        System.out.println("result : " + result);
        System.out.println("insert result : " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/product/update/{id}")
    public ResponseEntity<Integer> productUpdate(@PathVariable("id") String id, MultipartHttpServletRequest request) {
        ProductDTO productDTO = ProductDTO.builder().id(id).size(request.getParameter("size")).colorId(request.getParameter("colorId")).name(request.getParameter("name")).brand(request.getParameter("brand")).categoryLarge(request.getParameter("categoryLarge")).categoryMedium(request.getParameter("categoryMedium")).categorySmall(request.getParameter("categorySmall")).detail(request.getParameter("detail")).price(Integer.parseInt(request.getParameter("price"))).stockAmount(request.getParameter("stockAmount")).image1Uri(request.getParameter("savedImage1")) //사전에 저장되어있던 값들
                .image2Uri(request.getParameter("savedImage2")).image3Uri(request.getParameter("savedImage3")).build();

        List<MultipartFile> files = new ArrayList<>();
        if (!request.getFiles("image1Uri").isEmpty() && request.getFiles("image1Uri").get(0).getSize() != 0) {
            files.add(request.getFile("image1Uri"));
        }
        if (!request.getFiles("image2Uri").isEmpty() && request.getFiles("image2Uri").get(0).getSize() != 0) {
            files.add(request.getFile("image2Uri"));
        }
        if (!request.getFiles("image3Uri").isEmpty() && request.getFiles("image3Uri").get(0).getSize() != 0) {
            files.add(request.getFile("image3Uri"));
        }

        String colorChip = productService.getColorChip(productDTO.getColorId());
        productDTO.setColorChipUri(colorChip);

        //수정 부분
        int result = productService.updateProductInfo(productDTO, files);

        System.out.println("update result : " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("product/select")
    public Map<String, Object> getProductInfoList(@RequestBody SelectProductCriteria selectProductCriteria) {
        Map<String, Object> map = new HashMap<>();

        //전체 상품수 count
        int count = productService.searchProductCount(selectProductCriteria);

        //검색 로직 구현하기
        List<ProductDTO> productList = productService.getProductSearchList(selectProductCriteria);
        PageDTO pageDTO = new PageDTO(selectProductCriteria, count);
        map.put("page", pageDTO);
        map.put("list", productList);

        return map;
    }

    /**
     * dateType : 데이터 기간 단위<br>
     * dataType : 데이터 종류(count, price)<br>
     *
     * @param requestMap
     * @return
     */
    @PostMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> responseMap = new HashMap<>();

        // 예외 처리
        // DateType 검사
        String dateTypeStr = (String) requestMap.get("dateType");
        DateType dateType;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        try {
            dateType = DateType.valueOf(dateTypeStr.toUpperCase());
            switch (dateType) {
                case YEAR:
                    calendar.add(Calendar.YEAR, -5);
                    break;
                case MONTH:
                    calendar.add(Calendar.MONTH, -12);
                    break;
                case DATE:
                    calendar.add(Calendar.DATE, -7);
                    break;
                case HOUR:
                    calendar.add(Calendar.HOUR, -24);
                    break;
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // dataType 검사
        String dataTypeStr = (String) requestMap.get("dataType");
        Map<String, Integer> dataMap = null;

        switch (dataTypeStr) {
            case "salesCount":
                dataMap = orderService.getSalesCount(calendar.getTime(), dateType);
                break;
            case "revenue":
                dataMap = orderService.getRevenue(calendar.getTime(), dateType);
                break;
            case "customers":
                dataMap = orderService.getCustomerCount(calendar.getTime(), dateType);
                break;
        }

        System.out.println(dataMap);

        responseMap.put("data", dataMap);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    /**
     * VIP 회원 상세 정보<br>
     *
     * @param requestMap
     * @return
     */
    @PostMapping("/vip/detail")
    public ResponseEntity<Map<String, Object>> getMemberDetail(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> responseMap = new HashMap<>();

        String id = (String) requestMap.get("id");
        if (id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Member2DTO member = memberService.findByEmail(id);
        member.setPassword("");
        responseMap.put("data", member);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/vip/statistics/login")
    public ResponseEntity<Map<String, Object>> getLoginStatistics(@RequestBody Map<String, Object> requestMap) {

        String id = (String) requestMap.get("id");
        if (id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // 예외 처리
        // DateType 검사
        String dateTypeStr = (String) requestMap.get("dateType");
        DateType dateType;
        try {
            dateType = DateType.valueOf(dateTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 최근 기간별 로그인 횟수
        Map<String, Integer> data = memberService.getLoginCountByMemberId(id, dateType);
        Map<String, Object> responseMap = new HashMap<>();

        // 최근 데이터만 반환
        // 역순으로 키 정렬
        List<String> sortedKeyList = data.keySet()
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        int oldDateIdx = 0;
        switch (dateType) {
            case YEAR:
                oldDateIdx = 5;
                break;
            case MONTH:
                oldDateIdx = 12;
                break;
            case DATE:
                oldDateIdx = 7;
                break;
            case HOUR:
                oldDateIdx = 24;
                break;
            case MINUTE:
                oldDateIdx = 60;
                break;
        }
        Map<String, Integer> recentData = new HashMap<>();
        for (int i = 0; i < oldDateIdx; ++i) {
            recentData.put(sortedKeyList.get(i), data.get(sortedKeyList.get(i)));
        }

        responseMap.put("data", recentData);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/vip/statistics/order/price")
    public ResponseEntity<Map<String, Object>> getOrderedDateCount(@RequestBody Map<String, Object> requestMap) {

        String id = (String) requestMap.get("id");
        if (id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // 예외 처리
        // DateType 검사
        String dateTypeStr = (String) requestMap.get("dateType");
        DateType dateType;
        try {
            dateType = DateType.valueOf(dateTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("data", orderService.getOrderedDatePriceByMemberId(id, dateType));

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/vip/statistics/order/brand")
    public ResponseEntity<Map<String, Object>> getOrderedBrandCount(@RequestBody Map<String, Object> requestMap) {

        String id = (String) requestMap.get("id");
        if (id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("data", orderService.getOrderedBrandCountByMemberId(id));

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/vip/statistics/order/category")
    public ResponseEntity<Map<String, Object>> getOrderedCategoryCount(@RequestBody Map<String, Object> requestMap) {

        String id = (String) requestMap.get("id");
        if (id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("data", orderService.getOrderedCategoryCountByMemberId(id));

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }


}
