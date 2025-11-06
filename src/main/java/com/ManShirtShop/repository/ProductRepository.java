package com.ManShirtShop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ManShirtShop.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = "SELECT * FROM product WHERE product.status = 0 ORDER BY product.create_time DESC", nativeQuery = true)
    List<Product> getAllByStatus();
    @Query(value = "Select product.* from product\n" + //
                "join product_detail on product.id = product_detail.product_id\n" + //
                "where product.category_id in (:category)\n" + //
                "and product.collar_id in (:collar) and product.design_id in (:design)\n" + //
                "and product.form_id in (:form) and product.material_id in (:material)\n" + //
                "and product.sleeve_id in (:sleeve) and product_detail.size_id in (:size) and product_detail.color_id in (:color)\n" +//
                "and product.status = :status\n" +//
                "and product.price >= :low and product.price <= :high group by product.id"
                , nativeQuery = true)
    List<Product> getAllByAll(@Param("category") List<Integer> Category,
                             @Param("collar") List<Integer> Collar,
                             @Param("design") List<Integer> Design,
                             @Param("form") List<Integer> Form,
                             @Param("material") List<Integer> Material,
                             @Param("sleeve") List<Integer> Sleeve,
                             @Param("size") List<Integer> Size,
                             @Param("color") List<Integer> Color,
                             @Param("status") Integer Status,
                             @Param("low") Double Low,
                             @Param("high") Double High);
        @Query(value = "SELECT product.price FROM product WHERE product.status = 0 ORDER BY product.price ASC Limit 1", nativeQuery = true)
        Double getLow();   
        @Query(value = "SELECT product.price FROM product WHERE product.status = 0 ORDER BY product.price DESC Limit 1", nativeQuery = true)
        Double getHigh();               
}
