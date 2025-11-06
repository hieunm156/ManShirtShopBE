package com.ManShirtShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ManShirtShop.entities.Order;

public interface OderRepository extends JpaRepository<Order,Integer>{
    
}
