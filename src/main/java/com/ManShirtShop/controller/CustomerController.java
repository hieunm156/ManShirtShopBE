package com.ManShirtShop.controller;

import com.ManShirtShop.dto.contact.ContactRequest;
import com.ManShirtShop.dto.contact.ContactResponse;
import com.ManShirtShop.dto.customer.CustomerRequest;
import com.ManShirtShop.dto.customer.CustomerResponse;
import com.ManShirtShop.service.contact.ContactService;
import com.ManShirtShop.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/customer")
@Tag(name = "Customer api")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Operation(summary = "Lấy ra danh sách Account Khách hàng đang hoạt động")
    @GetMapping(value =  "findAllActive")
    public ResponseEntity<List<CustomerResponse>> getAllActive(){
        return ResponseEntity.ok(customerService.getAllActive());
    }
    @Operation(summary = "Lấy ra danh sách Account Khách hàng ngừng hoạt động")
    @GetMapping(value =  "findAllDisActive")
    public ResponseEntity<List<CustomerResponse>> getAllDisActive(){
        return ResponseEntity.ok(customerService.getAllDisActive());
    }
    @Operation(summary = "Thêm khách hàng")
    @PostMapping(value =  "create",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok().body(customerService.create(customerRequest));
    }

    @Operation(summary = "Vô hiệu hoá Account khách hàng")
    @DeleteMapping(value = "delete")
    public ResponseEntity<CustomerResponse> delete(@RequestParam Integer id){
        return ResponseEntity.ok().body(customerService.delete(id));
    }
    @Operation(summary = "Sửa thông tin khách hàng")
    @PostMapping(value =  "update",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CustomerResponse> update(@RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok().body(customerService.update(customerRequest));
    }
}
