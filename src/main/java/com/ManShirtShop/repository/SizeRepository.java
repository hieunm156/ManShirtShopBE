package com.ManShirtShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ManShirtShop.entities.Size;

public interface SizeRepository extends JpaRepository<Size,Integer>{
    @Query(value = "SELECT size.id FROM size WHERE size.status = 0", nativeQuery = true)
    List<Integer> findAllId();
}
