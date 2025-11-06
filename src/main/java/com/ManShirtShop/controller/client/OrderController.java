package com.ManShirtShop.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ManShirtShop.dto.client.oderDto.OderRequest;
import com.ManShirtShop.service.client.oder.OderService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "client/api/order")
@Tag(name = "Order Client api")
public class OrderController {

    @Autowired
    OderService oderService;

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> create(@RequestBody OderRequest requet) {
        System.out.println(requet.toString());
        return ResponseEntity.ok(oderService.create(requet));
    }

    @GetMapping(value = "findById")
    public ResponseEntity<?> findById(@RequestParam Integer id) {
        return ResponseEntity.ok(oderService.findById(id));
    }

}
