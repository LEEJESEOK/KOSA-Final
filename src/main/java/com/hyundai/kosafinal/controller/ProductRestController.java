package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.*;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LEE JESEOK
 */
@RestController
@RequestMapping("/api")
public class ProductRestController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/category/{large}_{medium}_{small}_{pageNum}_{brand}_{color}_{size}")
    public Map<String, Object> productList(@PathVariable("large") String large, @PathVariable("medium") String medium, @PathVariable("small") String small,
                                           @PathVariable(value = "pageNum", required = false) Integer pageNum, @PathVariable("brand") String brand,
                                           @PathVariable("color") String color, @PathVariable("size") String size,
                                           @RequestParam(value = "minPrice", required = false) Integer minPrice, @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                           @RequestParam(value = "sortType", required = false) Integer sortType) {

        String id = "";
        Map<String, Object> map = new HashMap<>();

        CategoryDTO categoryDTO = new CategoryDTO(large, medium, small);
        map.put("category", categoryDTO);

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
        if(sortType == null)
            sortType = 0;

        int count = productService.getFilteredProductCount(id, size, color, categoryDTO, brand, minPrice, maxPrice);
        map.put("count", count);

        PageDTO pageDTO = new PageDTO(criteria, count);
        map.put("page", pageDTO);

        List<ProductDTO> productList = productService.getFilteredProductListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
        map.put("productList", productList);

        List<List<ColorDTO>> colorList = new ArrayList<>();
        List<List<SizeDTO>> sizeList = new ArrayList<>();
        for (int i = 0; i < productList.size(); ++i) {

            List<ColorDTO> colors = productService.getFilteredColorListWithPaging(productList.get(i).getId(), size, color, categoryDTO, brand, minPrice, maxPrice, sortType, null);
            colorList.add(colors);
            List<SizeDTO> sizes = productService.getFilteredSizeListWithPaging(productList.get(i).getId(), size, color, categoryDTO, brand, minPrice, maxPrice, sortType, null);
            sizeList.add(sizes);
        }
        map.put("colorList", colorList);
        map.put("sizeList", sizeList);

        System.out.println(categoryDTO);
        return map;
    }

    @GetMapping("/category/list")
    public Map<String, Map<String, List<String>>> getCategoryList(){

        List<CategoryDTO> ctyList = productService.getCategoryList();
        Map<String, Map<String, List<String>>> mapList = new HashMap<>();

        //large ???????????? ??????
        for(CategoryDTO large : ctyList){
            if(!mapList.containsKey(large.getLarge())){
                mapList.put(large.getLarge(), new HashMap<>());
            }
        }

        //medium ???????????? ??????
        for (CategoryDTO medium : ctyList){
            if(!mapList.get(medium.getLarge()).containsKey(medium.getMedium())){
                mapList.get(medium.getLarge()).put(medium.getMedium(), new ArrayList<>());
            }
        }

        //small ???????????? ??????
        for (CategoryDTO small : ctyList){
            if(!mapList.get(small.getLarge()).get(small.getMedium()).contains(small.getSmall())){
                mapList.get(small.getLarge()).get(small.getMedium()).add(small.getSmall());
            }
        }
        System.out.println(mapList);
        return mapList;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductInfo(@PathVariable("id") String id,
      @RequestParam(value = "size") String size,
      @RequestParam(value = "categoryLarge") String categoryLarge,
      @RequestParam(value = "categoryMedium") String categoryMedium,
      @RequestParam(value = "categorySmall") String categorySmall,
      @RequestParam(value = "colorId") String colorId){
        CategoryDTO categoryDTO = new CategoryDTO(categoryLarge, categoryMedium, categorySmall);
        List<ProductDTO> productDTOS = productService.getProductDetailList(id, size, colorId, categoryDTO);
        return new ResponseEntity<>(productDTOS.get(0), HttpStatus.OK);
    }
}
