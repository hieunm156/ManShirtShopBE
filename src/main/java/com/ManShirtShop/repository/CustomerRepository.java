package com.ManShirtShop.repository;

import com.ManShirtShop.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    @Query(value = "SELECT * FROM customer WHERE status = 0 ORDER BY customer.create_time DESC", nativeQuery = true)
    List<Customer> getAllActiveByStatus();

    @Query(value = "SELECT * FROM customer WHERE status = 1 ORDER BY customer.create_time DESC", nativeQuery = true)
    List<Customer> getAllDisActiveByStatus();
    
    Optional<Customer> findByEmail(String email);
}
