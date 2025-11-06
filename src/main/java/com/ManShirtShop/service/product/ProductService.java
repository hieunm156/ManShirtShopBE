package com.ManShirtShop.service.product;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ManShirtShop.dto.product.ProductFilterRequest;
import com.ManShirtShop.dto.product.ProductReponse;
import com.ManShirtShop.dto.product.ProductRequest;
import com.ManShirtShop.dto.product_Detail_Image_Dto.ProductAllRequest;

public interface ProductService {
    List<ProductReponse> getAll();

    List<ProductReponse> getAllByFilter(ProductFilterRequest filter);

    ProductReponse create(ProductRequest requet);

    ProductReponse update(ProductAllRequest requet);

    ProductReponse delete(Integer id);

    ProductReponse findById(Integer id);

    ProductReponse createProductDetailImage(ProductAllRequest request);

   
}
