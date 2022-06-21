package com.hyundai.kosafinal.mapper.product;

import com.hyundai.kosafinal.domain.CategoryDTO;
import com.hyundai.kosafinal.domain.ColorDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.SizeDTO;
import com.hyundai.kosafinal.entity.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    // 상품 상세 정보
    List<ProductDTO> getProductDetailList(@Param("id") String id, @Param("size") String size, @Param("colorId") String colorId, @Param("category") CategoryDTO categoryDTO);

    // 카테고리에 해당하는 제품 목록
    List<ProductDTO> selectProductListByCategory(CategoryDTO categoryDTO);

    // 필터에 해당하는 제품 목록, 페이징 적용
    List<ProductDTO> selectFilteredProductListWithPaging(@Param("id") String id, @Param("size") String size, @Param("colorId") String colorId,
                                                         @Param("category") CategoryDTO categoryDTO,
                                                         @Param("brand") String brand, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice,
                                                         @Param("sortType") int sortType, @Param("criteria") Criteria criteria);

    List<ColorDTO> selectFilteredColorListWithPaging(@Param("id") String id, @Param("size") String size, @Param("colorId") String colorId,
                                                     @Param("category") CategoryDTO categoryDTO,
                                                     @Param("brand") String brand, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice,
                                                     @Param("sortType") int sortType, @Param("criteria") Criteria criteria);

    List<SizeDTO> selectFilteredSizeListWithPaging(@Param("id") String id, @Param("size") String size, @Param("colorId") String colorId,
                                                   @Param("category") CategoryDTO categoryDTO,
                                                   @Param("brand") String brand, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice,
                                                   @Param("sortType") int sortType, @Param("criteria") Criteria criteria);

    // 전체 제품 수
    int getTotalCount();

    // 카테고리에 해당하는 제품 수
    int getCategoryCount(CategoryDTO categoryDTO);

    // 필터에 해당하는 제품 수
    int getFilteredProductListCount(@Param("id") String id, @Param("size") String size, @Param("colorId") String colorId,
                                    @Param("category") CategoryDTO categoryDTO,
                                    @Param("brand") String brand, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice);

    // 카테고리 전체 목록
    List<CategoryDTO> selectCategory();

    // 새로운 상품 추가
    int insertProduct(ProductDTO productDTO);

    // 상품 정보 수정
    int updateProduct(ProductDTO productDTO);
    
    List<ProductDTO> selectProductDetailById(@Param("productId")String productId);
}
