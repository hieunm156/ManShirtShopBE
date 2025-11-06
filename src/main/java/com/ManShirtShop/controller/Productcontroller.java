package com.ManShirtShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ManShirtShop.dto.product.ProductFilterRequest;
import com.ManShirtShop.dto.product.ProductReponse;
import com.ManShirtShop.dto.product.ProductRequest;
import com.ManShirtShop.dto.product_Detail_Image_Dto.ProductAllRequest;
import com.ManShirtShop.service.product.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.awt.image.BufferedImage;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import java.awt.Font;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "api/product")
@Tag(name = "Product api")
public class Productcontroller {
    @Autowired
    ProductService productService;

    @Operation(summary = "Lấy danh sách ")
    @GetMapping(value = "getAll")
    public List<ProductReponse> getAll() {
        return productService.getAll();
    }

    @Operation(summary = "Lấy ra 1 sản phẩm theo id sản phẩm ")
    @GetMapping(value = "findById")
    public ProductReponse findById(@Parameter(description = "id của sản phẩm") @RequestParam Integer id) {
        return productService.findById(id);
    }

    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductReponse> create(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok().body(productService.create(productRequest));
    }

    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductReponse> update(@RequestBody ProductAllRequest productRequest) {
        return ResponseEntity.ok().body(productService.update(productRequest));
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<ProductReponse> delete(@RequestParam Integer id) {
        return ResponseEntity.ok().body(productService.delete(id));
    }

    @PostMapping(value = "savePDI", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductReponse> create(@RequestBody ProductAllRequest orderData) {
        System.out.println(orderData.toString());
        return ResponseEntity.ok().body(productService.createProductDetailImage(orderData));
    }

    @PostMapping(value = "getAllByFilter", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public List<ProductReponse> getAllByFilter(@RequestBody ProductFilterRequest filterData) {
        return productService.getAllByFilter(filterData);
    }

}
