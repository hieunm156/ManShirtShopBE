package com.ManShirtShop.repository;

import com.ManShirtShop.entities.Discount;
import com.ManShirtShop.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
    @Query(value = "SELECT * FROM voucher WHERE voucher.status = 0", nativeQuery = true)
    List<Voucher> getAllByStatus();
    boolean existsByCode(String code);
}
