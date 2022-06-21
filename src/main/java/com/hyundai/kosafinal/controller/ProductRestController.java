package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.*;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LEE JESEOK
 */
@RestController
public class ProductRestController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/category/{large}_{medium}_{small}_{pageNum}_{brand}")
    public Map<String, Object> productList(@PathVariable("large") String large, @PathVariable("medium") String medium, @PathVariable("small") String small,
                                           @PathVariable("pageNum") int pageNum, @PathVariable("brand") String brand) {
        String id = "";
        String size = "";
        String color = "";
        int minPrice = 0, maxPrice = Integer.MAX_VALUE;
        CategoryDTO categoryDTO = new CategoryDTO(large, medium, small);
        Criteria criteria = new Criteria(pageNum, 12);

        int count = productService.getFilteredProductCount(id, size, color, categoryDTO, brand, minPrice, maxPrice);
        PageDTO pageDTO = new PageDTO(criteria, count);

        List<ProductDTO> productList = productService.getFilteredProductListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, 0, criteria);
        List<ColorDTO> colorList = productService.getFilteredColorListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, 0, criteria);
        List<SizeDTO> sizeList = productService.getFilteredSizeListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, 0, criteria);

        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("page", pageDTO);
        map.put("productList", productList);
        map.put("colorList", colorList);
        map.put("sizeList", sizeList);

        System.out.println(categoryDTO);
        return map;
    }
}
