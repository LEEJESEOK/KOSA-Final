package com.hyundai.kosafinal.mapper.product;

import com.hyundai.kosafinal.domain.CategoryDTO;
import com.hyundai.kosafinal.domain.Criteria;
import com.hyundai.kosafinal.domain.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import sun.util.resources.cldr.to.CalendarData_to_TO;

import java.util.List;

@Mapper
public interface ProductMapper {
    // 새로운 상품 추가
    int insertProduct(ProductDTO productDTO);

    // 상품 정보 수정
    int updateProduct(ProductDTO productDTO);

    // id, 이름, 카테고리, 브랜드 정보로 상품 조회
    List<ProductDTO> selectSimpleProductListByCategory(CategoryDTO categoryDTO);

    List<ProductDTO> selectSimpleProductListByCategoryWithPage(CategoryDTO categoryDTO, Criteria criteria);


    ProductDTO selectDetailProduct(String id, String size, String colorId, String large, String medium, String small);


}
