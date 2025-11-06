package com.ManShirtShop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ManShirtShop.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query(value = "SELECT * FROM employee WHERE employee.status = 0 ORDER BY employee.create_time DESC", nativeQuery = true)
    List<Employee> getAllByStatus();
    
    Optional<Employee> findByEmail(String email);
}
