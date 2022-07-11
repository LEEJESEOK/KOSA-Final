package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.*;
import com.hyundai.kosafinal.entity.Criteria;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LEE JESEOK
 */
public interface ProductService {
    /**
     * 조건에 해당하는 상품 상세 정보<br>
     * 사이즈, 색상 정보 포함<br>
     *
     * @param id          상품 id
     * @param size        사이즈
     * @param color       색상
     * @param categoryDTO 분류
     * @return 조건에 해당하는 상품 상세 정보
     */
    List<ProductDTO> getProductDetailList(String id, String size, String color,
                                          CategoryDTO categoryDTO);

    /**
     * 조건에 해당하는 상품 목록<br>
     * 사이즈, 색상 정보 포함하지 않음<br>
     *
     * @param id          상품 코드
     * @param size        사이즈
     * @param color       색상
     * @param categoryDTO 분류
     * @param brand       브랜드 이름
     * @param minPrice    최소 가격
     * @param maxPrice    최대 가격
     * @param sortType    정렬 방법 (1:고가순, 2:저가순)
     * @param criteria    페이지 정보
     * @return 조건에 해당하는 상품 목록
     */
    List<ProductDTO> getFilteredProductListWithPaging(String id, String size, String color,
                                                      CategoryDTO categoryDTO, String brand,
                                                      int minPrice, int maxPrice, int sortType,
                                                      Criteria criteria);

    /**
     * 필터, 페이지 적용한 상품별 색상 목록<br>
     *
     * @param id          상품 코드
     * @param size        사이즈
     * @param color       색상
     * @param categoryDTO 분류
     * @param brand       브랜드 이름
     * @param minPrice    최소 가격
     * @param maxPrice    최대 가격
     * @param criteria    페이지 정보
     * @return 상품별 색상 목록
     */
    List<ColorDTO> getFilteredColorListWithPaging(String id, String size, String color,
                                                  CategoryDTO categoryDTO, String brand,
                                                  int minPrice, int maxPrice, int sortType,
                                                  Criteria criteria);

    /**
     * 필터, 페이지 적용한 상품별 사이즈 목록<br>
     *
     * @param id          상품 코드
     * @param size        사이즈
     * @param color       색상
     * @param categoryDTO 분류
     * @param brand       브랜드 이름
     * @param minPrice    최소 가격
     * @param maxPrice    최대 가격
     * @param criteria    페이지 정보
     * @return 상품별 사이즈 목록
     */
    List<SizeDTO> getFilteredSizeListWithPaging(String id, String size, String color,
                                                CategoryDTO categoryDTO, String brand,
                                                int minPrice, int maxPrice, int sortType,
                                                Criteria criteria);

    /**
     * 조건에 해당하는 상품 개수<br>
     *
     * @param id          상품 코드
     * @param size        개수
     * @param color       색상
     * @param categoryDTO 분류
     * @param brand       브랜드 이름
     * @param minPrice    최소 가격
     * @param maxPrice    최대 가격
     * @return 조건에 해당하는 상품 개수
     */
    int getFilteredProductCount(String id, String size, String color, CategoryDTO categoryDTO, String brand, int minPrice, int maxPrice);

    int saveProductInfo(ProductDTO productDTO, List<MultipartFile> files);

    List<CategoryDTO> getCategoryList();

    String getColorChip(String colorId);

    // 타임세일 상품 가져오기
    List<SaleDTO> getSaleProduct();

    // 한정상품 가져오기
    List<LimitedProductDTO> getLimitedProduct();
}
