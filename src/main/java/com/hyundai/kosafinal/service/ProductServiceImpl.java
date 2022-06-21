package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.CategoryDTO;
import com.hyundai.kosafinal.domain.ColorDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.SizeDTO;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.mapper.product.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LEE JESEOK
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProductDetailList(String id, String size, String color, CategoryDTO categoryDTO) {
        return productMapper.getProductDetailList(id, size, color, categoryDTO);
    }

    @Override
    public List<ProductDTO> getFilteredProductListWithPaging(String id, String size, String color, CategoryDTO categoryDTO, String brand, int minPrice, int maxPrice, int sortType, Criteria criteria) {
        return productMapper.selectFilteredProductListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
    }

    @Override
    public List<ColorDTO> getFilteredColorListWithPaging(String id, String size, String color,
                                                         CategoryDTO categoryDTO, String brand,
                                                         int minPrice, int maxPrice, int sortType,
                                                         Criteria criteria) {
        return productMapper.selectFilteredColorListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
    }

    @Override
    public List<SizeDTO> getFilteredSizeListWithPaging(String id, String size, String color, CategoryDTO categoryDTO, String brand, int minPrice, int maxPrice, int sortType, Criteria criteria) {
        return productMapper.selectFilteredSizeListWithPaging(id, size, color, categoryDTO, brand, minPrice, maxPrice, sortType, criteria);
    }

    @Override
    public int getFilteredProductCount(String id, String size, String color, CategoryDTO categoryDTO, String brand, int minPrice, int maxPrice) {
        return productMapper.getFilteredProductListCount(id, size, color, categoryDTO, brand, minPrice, maxPrice);
    }

}
