package com.ManShirtShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ManShirtShop.entities.OrderDetail;

public interface OrderDetailRepositoty extends JpaRepository<OrderDetail,Integer>{
    
}
