package com.ManShirtShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ManShirtShop.entities.Color;
import com.ManShirtShop.entities.Product;
import com.ManShirtShop.entities.ProductDetail;
import com.ManShirtShop.entities.Size;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,Integer>{
    @Query(value = "SELECT * FROM product_detail WHERE product_detail.status = 0 ORDER BY product_detail.create_time DESC", nativeQuery = true)
    List<ProductDetail> getAllByStatus();

    ProductDetail findByBarCode(String barCode);

    ProductDetail findByProductAndColorAndSize(Product product, Color color, Size size);
}
