package com.ManShirtShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ManShirtShop.entities.Discount;

public interface DiscountRepository extends JpaRepository<Discount,Integer> {
    @Query(value = "SELECT * FROM discount WHERE discount.status = 0 ORDER BY discount.create_time DESC", nativeQuery = true)
    List<Discount> getAllByStatus();
    @Query(value = "SELECT discount.id FROM discount WHERE discount.status = 0", nativeQuery = true)
    List<Integer> findAllId();
}
