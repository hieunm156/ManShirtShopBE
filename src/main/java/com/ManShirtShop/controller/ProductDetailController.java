package com.ManShirtShop.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ManShirtShop.dto.ProductDetail_Dto.ProductDetailRequest;
import com.ManShirtShop.dto.ProductDetail_Dto.ProductDetailResponse;
import com.ManShirtShop.service.productDetail.ProductDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping(value = "api/productDetail")
@Tag(name = "Product detail api")
public class ProductDetailController {
    @Autowired
    ProductDetailService productDetailService;

    @Operation(summary = "Lấy danh sách ")
    @GetMapping(value = "getAll")
    public List<ProductDetailResponse> getAll() {
        return productDetailService.getAll();
    }

    @Operation(summary = "Lấy ra 1 sản phẩm chi tiết theo id sản phẩm chi tiết")
    @GetMapping(value = "findById")
    public ProductDetailResponse findById(
            @Parameter(description = "id của sản phẩm chi tiết") @RequestParam Integer id) {
        return productDetailService.findById(id);
    }

    @Operation(summary = "Tạo mới 1 sản phẩm chi tiết")
    @PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductDetailResponse> create(@RequestBody ProductDetailRequest productDetailRequest) {
        return ResponseEntity.ok().body(productDetailService.Create(productDetailRequest));
    }

    @Operation(summary = "Sửa sản phẩm chi tiết")
    @PostMapping(value = "update", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductDetailResponse> update(@RequestBody ProductDetailRequest productDetailRequest) {
        return ResponseEntity.ok().body(productDetailService.update(productDetailRequest));
    }

    @Operation(summary = "Xoá sản phẩm chi tiết")
    @DeleteMapping(value = "delete")
    public ResponseEntity<ProductDetailResponse> delete(@RequestParam Integer id) {
        return ResponseEntity.ok().body(productDetailService.delete(id));
    }

    @Operation(summary = "Tạo mới nhiều sản phẩm chi tiết")
    @PostMapping(value = "createAll", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<ProductDetailResponse>> create(
            @RequestBody List<ProductDetailRequest> productDetailRequest) {
        return ResponseEntity.ok().body(productDetailService.saveAll(productDetailRequest));
    }

    @GetMapping(value = "findByBarcode")
    public ProductDetailResponse findByBarcode(
            @Parameter(description = "id của sản phẩm chi tiết") @RequestParam String barcode) {
        return productDetailService.findByBarcode(barcode);
    }

    @GetMapping(value = "getBarcode", produces = IMAGE_PNG_VALUE)
    public ResponseEntity<?> getBarcode(@RequestParam String barCodeRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(productDetailService.getBarcode(barCodeRequest));
    }
}
