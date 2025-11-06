package com.ManShirtShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ManShirtShop.dto.employee.employeeRequest;
import com.ManShirtShop.dto.employee.employeeResponse;
import com.ManShirtShop.service.employee.employeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "api/employee")
@Tag(name = "employee api")
public class EmployeeController {

    @Autowired
    employeeService employeeService;


    @GetMapping(value="getAll")
    public List<employeeResponse> getAll() {
        return employeeService.getAll();
    }

    @GetMapping(value="findById")
    public employeeResponse findById(@Parameter(description = "id của sản phẩm chi tiết") @RequestParam Integer id) {
        return employeeService.findById(id);
    }
    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<employeeResponse> create(@RequestBody employeeRequest productDetailRequest) {
        return ResponseEntity.ok().body(employeeService.Create(productDetailRequest));
    }
    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<employeeResponse> update(@RequestBody employeeRequest productDetailRequest) {
        return ResponseEntity.ok().body(employeeService.update(productDetailRequest));
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<employeeResponse> delete(@RequestParam Integer id) {
        return ResponseEntity.ok().body(employeeService.delete(id));
    }
}
