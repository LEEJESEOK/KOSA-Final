package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.*;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LEE JESEOK
 */
@RestController
@RequestMapping("/api/category")
public class ProductRestController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/{large}_{medium}_{small}_{pageNum}_{brand}_{color}_{size}")
    public Map<String, Object> productList(@PathVariable("large") String large, @PathVariable("medium") String medium, @PathVariable("small") String small,
                                           @PathVariable(value = "pageNum", required = false) Integer pageNum, @PathVariable("brand") String brand,
                                           @PathVariable("color") String color, @PathVariable("size") String size,
                                           @RequestParam(value = "minPrice", required = false) Integer minPrice, @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                           @RequestParam(value = "sortType", required = false) Integer sortType) {

        System.out.println("pageNum : " + pageNum);

        String id = "";

        CategoryDTO categoryDTO = new CategoryDTO(large, medium, small);
        if (pageNum == null) {
            pageNum = 1;
        }
        Criteria criteria = new Criteria(pageNum, 12);
        if (color == null)
            color = "";
        if (size == null)
            size = "";
        if (minPrice == null)
            minPrice = 0;
        if (maxPrice == null)
            maxPrice = Integer.MAX_VALUE;
        minPrice = Math.max(0, Math.min(minPrice, Integer.MAX_VALUE));
        maxPrice = Math.min(Integer.MAX_VALUE, Math.max(maxPrice, 0));

        int count = productService.getFilteredProductCount(id, size, color, categoryDTO, brand, minPrice, maxPrice);
        PageDTO pageDTO = new PageDTO(criteria, count);

        List<ProductDTO> productList = productService.getFilteredProductListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, 0, criteria);
        List<ColorDTO> colorList = productService.getFilteredColorListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, 0, criteria);
        List<SizeDTO> sizeList = productService.getFilteredSizeListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, 0, criteria);

        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryDTO);
        map.put("count", count);
        map.put("page", pageDTO);
        map.put("productList", productList);
        map.put("colorList", colorList);
        map.put("sizeList", sizeList);

        System.out.println(categoryDTO);
        return map;
    }
}
