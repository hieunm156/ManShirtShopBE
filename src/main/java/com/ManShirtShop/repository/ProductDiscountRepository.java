package com.ManShirtShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ManShirtShop.entities.Discount;
import com.ManShirtShop.entities.Product;
import com.ManShirtShop.entities.ProductDiscount;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount,Integer>{
    @Query(value = "SELECT * FROM product_discount WHERE product_discount.status = 0 ORDER BY product_discount.create_time DESC", nativeQuery = true)
    List<ProductDiscount> getAllByStatus();

    @Query(value = "SELECT a FROM ProductDiscount a WHERE a.productId = :productId AND a.discountId = :discountId")
    ProductDiscount getByProductIdAndDiscount(Product productId, Discount discountId);

    
}
