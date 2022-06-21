package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.CategoryDTO;
import com.hyundai.kosafinal.domain.ColorDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.SizeDTO;
import com.hyundai.kosafinal.entity.Criteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author LEE JESEOK
 */
@SpringBootTest
class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Test
    void getProductDetailList() {
        String id = "MM2B3WJS085M";
        String size = "";
        String colorId = "DN";
        CategoryDTO categoryDTO = new CategoryDTO("", "", "");
        System.out.println(productService.getProductDetailList(id, size, colorId, categoryDTO));
    }

    @Test
    void getFilteredProductListWithPaging() {
        String id = "";
        String size = "";
        String color = "";
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "");
        String brand = "TIME";
        int minPrice = 100000, maxPrice = 600000;
        int sortType = 0;
        Criteria criteria = new Criteria(2, 12);

        List<ProductDTO> list = productService.getFilteredProductListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
        for (ProductDTO productDTO : list)
            System.out.println(productDTO);
    }

    @Test
    void getFilteredColorListWithPaging() {
        String id = "";
        String size = "";
        String color = "";
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "");
        String brand = "TIME";
        int minPrice = 100000, maxPrice = 600000;
        int sortType = 0;
        Criteria criteria = new Criteria(2, 12);

        List<ColorDTO> list = productService.getFilteredColorListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
        for (ColorDTO colorDTO : list)
            System.out.println(colorDTO);
    }

    @Test
    void getFilteredSizeListWithPaging() {
        String id = "";
        String size = "";
        String color = "";
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "");
        String brand = "TIME";
        int minPrice = 100000, maxPrice = 600000;
        int sortType = 0;
        Criteria criteria = new Criteria(2, 12);

        List<SizeDTO> list = productService.getFilteredSizeListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
        for (SizeDTO sizeDTO : list)
            System.out.println(sizeDTO);
    }

    @Test
    void getFilteredProductCount() {
        String id = "";
        String size = "";
        String color = "";
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "");
        String brand = "TIME";
        int minPrice = 100000, maxPrice = 600000;
        System.out.println(productService.getFilteredProductCount(id, size, color, categoryDTO, brand, minPrice, maxPrice));
    }
}