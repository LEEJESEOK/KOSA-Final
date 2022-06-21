package com.hyundai.kosafinal.mapper.product;

import com.hyundai.kosafinal.domain.CategoryDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;


    @Test
    void selectSimpleProductList() {
        CategoryDTO categoryDTO = new CategoryDTO("WOMEN", "Top", "Blouse");
        List<ProductDTO> productDTOList = productMapper.selectSimpleProductListByCategory(categoryDTO);

        for (ProductDTO productDTO : productDTOList) {
            if(productDTOList.indexOf(productDTO) < 10)
            System.out.println(productDTO);
        }
    }

    @Test
    void selectProductDetailByIdTests(){
        List<ProductDTO> productDTOList = productMapper.selectProductDetailById("MM2B3WJS085M");

        productDTOList.forEach(dto -> {log.info(dto.toString());});
    }

}