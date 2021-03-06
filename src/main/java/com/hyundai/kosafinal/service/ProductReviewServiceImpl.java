package com.hyundai.kosafinal.service;

import com.hyundai.kosafinal.domain.ProductReviewDTO;
import com.hyundai.kosafinal.domain.SelectProductReviewCriteria;
import com.hyundai.kosafinal.mapper.product.ProductReviewMapper;
import com.hyundai.kosafinal.util.s3.S3Client;
import com.hyundai.kosafinal.util.s3.config.S3.Bucket;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private ProductReviewMapper productReviewMapper;

    private S3Client s3Client;

    public ProductReviewServiceImpl(
            ProductReviewMapper productReviewMapper,
            S3Client s3Client
    ) {
        this.productReviewMapper = productReviewMapper;
        this.s3Client = s3Client;
    }

    @Override
    public List<ProductReviewDTO> getProductReviewByProductId(SelectProductReviewCriteria selectProductReviewCriteria) {
        return productReviewMapper.getProductReviewByProductId(selectProductReviewCriteria);
    }

    @Override
    public List<ProductReviewDTO> getProductReviewByProductIdAndImg(SelectProductReviewCriteria selectProductReviewCriteria) {
        return productReviewMapper.getProductReviewByProductIdAndImg(selectProductReviewCriteria);
    }

    @Override
    public List<ProductReviewDTO> getProductReviewByProductIdAndText(SelectProductReviewCriteria selectProductReviewCriteria) {
        return productReviewMapper.getProductReviewByProductIdAndText(selectProductReviewCriteria);
    }

    @Override
    public List<ProductReviewDTO> getProductReviewAllList(String productId) {
        return productReviewMapper.getProductReviewAll(productId);
    }

    @Override
    public void saveProductReview(ProductReviewDTO productReviewDTO, MultipartFile imgFile) {
        if (imgFile != null) {
            this.uploadProfileImage(imgFile, productReviewDTO);
        }
        productReviewMapper.insertProductReview(productReviewDTO);
    }

    //?????? ?????? ??????
    @Override
    public int getProductReviewCountById(SelectProductReviewCriteria selectProductReviewCriteria) {
        return productReviewMapper.getProductReviewCount(selectProductReviewCriteria);
    }

    @Override
    public int getProductReviewCountByIdAndImg(SelectProductReviewCriteria selectProductReviewCriteria) {
        return productReviewMapper.getProductReviewCountByImg(selectProductReviewCriteria);
    }

    @Override
    public int getProductReviewCountByIdAndText(SelectProductReviewCriteria selectProductReviewCriteria) {
        return productReviewMapper.getProductReviewCountByText(selectProductReviewCriteria);
    }

    // ????????? ????????? -> ??? ?????? ????????????
    public String uploadProfileImage(
            MultipartFile image,
            ProductReviewDTO productReviewDTO
    ) {
        if (image.isEmpty()) {
            return null;
        }

        String key =
                "members/" + productReviewDTO.getEmail()
                        + "/product/"
                        + productReviewDTO.getProductId()
                        + "/"
                        + UUID.randomUUID()
                        + this.s3Client.getExtension(image);

        String profileKey = this.s3Client.upload(
                Bucket.IMAGE,
                image,
                key
        );
        productReviewDTO.setImgURI(profileKey);

        return key;
    }


    @Override
    public List<ProductReviewDTO> getProductReviewById(String id) {
        return productReviewMapper.selectProductReviewByEmail(id);
    }


}
