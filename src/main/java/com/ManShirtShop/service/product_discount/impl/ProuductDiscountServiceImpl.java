package com.ManShirtShop.service.product_discount.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.dsig.keyinfo.X509Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ManShirtShop.common.mapperUtil.ObjectMapperUtils;
import com.ManShirtShop.dto.product_Discount.ProductDiscountResponse;
import com.ManShirtShop.dto.product_Discount.ProductDiscoutRequest;
import com.ManShirtShop.entities.Discount;
import com.ManShirtShop.entities.Product;
import com.ManShirtShop.entities.ProductDiscount;
import com.ManShirtShop.repository.DiscountRepository;
import com.ManShirtShop.repository.ProductDiscountRepository;
import com.ManShirtShop.repository.ProductRepository;
import com.ManShirtShop.service.product_discount.ProductDiscountService;

@Service
public class ProuductDiscountServiceImpl implements ProductDiscountService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    ProductDiscountRepository productDiscountRepository;

    @Override
    public List<ProductDiscountResponse> getAll() {
        List<ProductDiscount> geListDb = productDiscountRepository.getAllByStatus();
        return ObjectMapperUtils.mapAll(geListDb, ProductDiscountResponse.class);
    }

    @Override
    public ResponseEntity<?> create(ProductDiscoutRequest request) {
        try {
            request.setId(-1);
            if (request.getPercent() <= 0 || request.getPercent() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phần trăm giảm giá không hợp lệ");
            }

            if (request.getDiscountId() == 0 || request.getDiscountId() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Discount");
            } else if (!discountRepository.existsById(request.getDiscountId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Discount");
            }

            if (request.getProductId().isEmpty() || request.getProductId() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Product cần giảm giá");
            }
            Discount discount = discountRepository.findById(request.getDiscountId()).get();
            List<ProductDiscount> listProductsDiscount = new ArrayList<>();
            for (Integer x : request.getProductId()) {
                ProductDiscount entity = new ProductDiscount();
                System.out.println("----------------=: " + x);
                if (x != 0 && x != null) {
                    if (!productRepository.existsById(x)) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Product cần giảm giá");
                    }
                    Product product = new Product();
                    product = productRepository.findById(x).get();
                    if (productDiscountRepository.getByProductIdAndDiscount(product, discount) != null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm đã tồn tại mã giảm giá");
                    }
                    entity.setProductId(product);
                    entity.setDiscountId(discount);
                    entity.setPercent(request.getPercent());
                    entity.setCreateBy("admin");
                    entity.setCreateTime(Timestamp.from(Instant.now()));
                    entity.setStatus(0);
                    listProductsDiscount.add(entity);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Product cần giảm giá");
                }
            }
            listProductsDiscount = productDiscountRepository.saveAll(listProductsDiscount);
            return ResponseEntity.ok(ObjectMapperUtils.mapAll(listProductsDiscount, ProductDiscountResponse.class));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @Override
    public ResponseEntity<?> update(List<ProductDiscoutRequest> request) {
        try {
            List<ProductDiscount> listProductsDiscount = new ArrayList<>();
            for (ProductDiscoutRequest productDiscoutRequest : request) {
                if (productDiscoutRequest.getPercent() <= 0 || productDiscoutRequest.getPercent() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phần trăm giảm giá không hợp lệ");
                }

                if (productDiscoutRequest.getDiscountId() == 0 || productDiscoutRequest.getDiscountId() == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Discount");
                } else if (!discountRepository.existsById(productDiscoutRequest.getDiscountId())) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Discount");
                }

                if (productDiscoutRequest.getProductId().isEmpty() || productDiscoutRequest.getProductId() == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Product cần giảm giá");
                }
                Discount discount = discountRepository.findById(productDiscoutRequest.getDiscountId()).get();
                for (Integer x : productDiscoutRequest.getProductId()) {

                    if (x != 0 && x != null) {
                        Product product = new Product();
                        if (!productRepository.existsById(x)) {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body("Không tìm thấy Product cần giảm giá");
                        }
                        product = productRepository.findById(x).get();
                        ProductDiscount entity = new ProductDiscount();
                        entity = productDiscountRepository.getByProductIdAndDiscount(product, discount);
                        if (entity == null) {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body("Không tìm thấy mã giảm giá của sản phẩm");
                        }
                        entity.setPercent(productDiscoutRequest.getPercent());
                        entity.setUpdateBy("admin");
                        entity.setUpdateTime(Timestamp.from(Instant.now()));
                        entity.setStatus(0);
                        listProductsDiscount.add(entity);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy Product cần giảm giá");
                    }
                }
            } // end fore
            listProductsDiscount = productDiscountRepository.saveAll(listProductsDiscount);
            return ResponseEntity.ok(ObjectMapperUtils.mapAll(listProductsDiscount, ProductDiscountResponse.class));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi không xác định vui lòng liên hệ quản trị viên");
        }
    }

    @Override
    public ResponseEntity<?> delete(List<Integer> id) {
        try {
            List<ProductDiscount> productDiscounts = new ArrayList<>();
            for (Integer x : id) {
                if (x <= 0 || id == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("không tìm thấy phần trăm giảm giá");
                }
                if (!productDiscountRepository.existsById(x)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("không tìm thấy phần trăm giảm giá");
                }
                ProductDiscount productDiscount = productDiscountRepository.findById(x).get();
                productDiscount.setStatus(1);
                productDiscounts.add(productDiscount);
            }
            productDiscounts = productDiscountRepository.saveAll(productDiscounts);
            return ResponseEntity.ok(ObjectMapperUtils.mapAll(productDiscounts, ProductDiscountResponse.class));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi không xác định vui lòng liên hệ quản trị viên");
        }
    }

    @Override
    public ResponseEntity<?> findById(Integer id) {
        if (id <= 0 || id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("không tìm thấy phần trăm giảm giá");
        }
        if (!productDiscountRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("không tìm thấy phần trăm giảm giá");
        }
        ProductDiscount productDiscount = productDiscountRepository.findById(id).get();
        return ResponseEntity.ok(ObjectMapperUtils.map(productDiscount, ProductDiscountResponse.class));
    }

    public Boolean checkId(Integer id) {
        if (id == 0 || id == null) {
            return false;
        }
        if (!discountRepository.existsById(id)) { // check id db
            return false;
        }
        return true;
    }

}
