package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.CategoryDTO;
import com.hyundai.kosafinal.domain.ColorDTO;
import com.hyundai.kosafinal.domain.ProductDTO;
import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.domain.SizeDTO;
import com.hyundai.kosafinal.entity.Criteria;
import com.hyundai.kosafinal.mapper.product.ProductMapper;
import com.hyundai.kosafinal.util.s3.S3Client;
import com.hyundai.kosafinal.util.s3.config.S3.Bucket;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LEE JESEOK
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private S3Client s3Client;

    @Value("${s3.bucket.address}")
    private String S3_BUCKET_ADDRESS;

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

    @Override
    public int saveProductInfo(
      ProductDTO productDTO,
      List<MultipartFile> files
    ) {
        //파일이 있다면 S3 이미지 생성후 url 저장하기
        if(files != null && !files.isEmpty()){
            for(int i=0;i<files.size();i++){
                this.uploadProfileImage(files.get(i), productDTO, i);
            }
        }
        return productMapper.insertProduct(productDTO);
    }

    @Override
    public List<CategoryDTO> getCategoryList() {
        return productMapper.selectCategory();
    }

    @Override
    public String getColorChip(String colorId) {
        return productMapper.getColorChipByPid(colorId);
    }

    // 업로드 이미지 -> 키 값을 가져오기(product의경우 full Path)
    public String uploadProfileImage(
      MultipartFile image,
      ProductDTO productDTO,
      int index
    ) {
        if (image.isEmpty()) {
            return null;
        }

        String key =
            "product/"
            + productDTO.getId()
            + "/"
            + UUID.randomUUID()
            + this.s3Client.getExtension(image);

        String profileKey = this.s3Client.upload(
          Bucket.IMAGE,
          image,
          key
        );

        if(index == 0) productDTO.setImage1Uri(S3_BUCKET_ADDRESS+profileKey);
        if(index == 1) productDTO.setImage2Uri(S3_BUCKET_ADDRESS+profileKey);
        if(index == 2) productDTO.setImage3Uri(S3_BUCKET_ADDRESS+profileKey);

        return key;
    }

}
