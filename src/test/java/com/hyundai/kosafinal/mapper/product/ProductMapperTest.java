package com.hyundai.kosafinal.mapper.product;

import com.hyundai.kosafinal.domain.CategoryDTO;
import com.hyundai.kosafinal.domain.ColorDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.SizeDTO;
import com.hyundai.kosafinal.entity.Criteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;


    @Test
    void getProductDetail() {
        String id = "MM2B3WJS085M";
        String size = "";
        String colorId = "DN";
        CategoryDTO categoryDTO = new CategoryDTO("MEN", "OUTER", "");
        List<ProductDTO> productDTO = productMapper.getProductDetailList(id, size, colorId, categoryDTO);
        System.out.println(productDTO);
    }

    @Test
    void selectProductListByCategory() {
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "Blouse");
        List<ProductDTO> productDTOList = productMapper.selectProductListByCategory(categoryDTO);

        for (ProductDTO productDTO : productDTOList) {
            System.out.println(productDTO);
        }
    }

    @Test
    void selectFilteredProductListWithPaging() {
        String id = "";
        String size = "";
        String color = "";
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "");
        String brand = "TIME";
        int minPrice = 100000, maxPrice = 600000;
        int sortType = 0;
        Criteria criteria = new Criteria(2, 12);
        List<ProductDTO> products = productMapper.selectFilteredProductListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
        for (ProductDTO productDTO : products)
            System.out.println(productDTO);
    }

    @Test
    void selectFilteredColorListWithPaging() {
        String id = "";
        String size = "";
        String color = "";
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "");
        String brand = "TIME";
        int minPrice = 100000, maxPrice = 600000;
        int sortType = 0;
        Criteria criteria = new Criteria(2, 12);
        List<ColorDTO> list = productMapper.selectFilteredColorListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
        for (ColorDTO colorDTO : list)
            System.out.println(colorDTO);
    }

    @Test
    void selectFilteredSizeListWithPaging() {
        String id = "";
        String size = "";
        String color = "";
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "");
        String brand = "TIME";
        int minPrice = 100000, maxPrice = 600000;
        int sortType = 0;
        Criteria criteria = new Criteria(2, 12);
        List<SizeDTO> products = productMapper.selectFilteredSizeListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
        for (SizeDTO sizeDTO : products)
            System.out.println(sizeDTO);
    }

    @Test
    void getTotalCount() {
        System.out.println(productMapper.getTotalCount());
    }

    @Test
    void getCategoryCount() {
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "");
        System.out.println(productMapper.getCategoryCount(categoryDTO));
    }

    @Test
    void getFilteredProductListCount() {
        String id = "";
        String size = "";
        String color = "";
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "Knit");
        String brand = "";
        int minPrice = 100000, maxPrice = 600000;
        int count = productMapper.getFilteredProductListCount(id, size, color, categoryDTO, brand, minPrice, maxPrice);
        System.out.println(count);

    }

    @Test
    void selectCategory() {
        List<CategoryDTO> list = productMapper.selectCategory();
        for (CategoryDTO categoryDTO : list)
            System.out.println(categoryDTO);
    }
}