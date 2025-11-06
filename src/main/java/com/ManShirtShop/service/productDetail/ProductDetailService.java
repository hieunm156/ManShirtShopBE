package com.ManShirtShop.service.productDetail;

import java.awt.image.BufferedImage;
import java.util.List;

import com.ManShirtShop.dto.ProductDetail_Dto.ProductDetailRequest;
import com.ManShirtShop.dto.ProductDetail_Dto.ProductDetailResponse;

public interface ProductDetailService {
    List<ProductDetailResponse> getAll();

    List<ProductDetailResponse> saveAll(List<ProductDetailRequest> request);

    ProductDetailResponse Create(ProductDetailRequest request);

    ProductDetailResponse update(ProductDetailRequest request);

    ProductDetailResponse delete(Integer id);

    ProductDetailResponse findById(Integer id);

    ProductDetailResponse findByBarcode(String barcode);

    BufferedImage getBarcode(String barCodeRequest);
}
