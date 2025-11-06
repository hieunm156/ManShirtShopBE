package com.ManShirtShop.service.client.oder.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.checkerframework.checker.units.qual.g;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ManShirtShop.common.mapperUtil.ObjectMapperUtils;
import com.ManShirtShop.dto.client.oderDetailDto.OderDetailResponse;
import com.ManShirtShop.dto.client.oderDto.OderRequest;
import com.ManShirtShop.dto.client.oderDto.OderResponse;
import com.ManShirtShop.dto.product_Image_dto.ProductImageResponse;
import com.ManShirtShop.entities.Employee;
import com.ManShirtShop.entities.Order;
import com.ManShirtShop.entities.OrderDetail;
import com.ManShirtShop.repository.CustomerRepolsitory;
import com.ManShirtShop.repository.EmployeeRepository;
import com.ManShirtShop.repository.OderRepository;
import com.ManShirtShop.repository.OrderDetailRepositoty;
import com.ManShirtShop.repository.VoucherRepository;
import com.ManShirtShop.service.client.oder.OderService;
import com.ManShirtShop.service.oderDetail.OderDetailService;
import com.ManShirtShop.service.oderDetail.impl.OderdetailServiceImpl;

@Service
public class OderserviceImpl implements OderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OderdetailServiceImpl.class);
    @Autowired
    OderRepository oderRepository;

    @Autowired
    OrderDetailRepositoty orderDetailRepositoty;

    @Autowired
    CustomerRepolsitory customerRepolsitory;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    OderDetailService oderDetailService;
    
    @Override
    public OderResponse create(OderRequest oderRequest) {
        Order order = ObjectMapperUtils.map(oderRequest, Order.class);
        if (checkId(oderRequest.getVoucher(), voucherRepository.existsById(oderRequest.getVoucher()))) {
            order.setVoucher(null);
        } else {
            order.setVoucher(voucherRepository.findById(oderRequest.getVoucher()).get());
        } // check voucherx`
        order.setCreateBy("Khác Hàng");
        order.setCreateTime(Timestamp.from(Instant.now()));
        order.setCustomer(null);
        order.setEmployee(null);
        order = oderRepository.save(order);
        order = oderRepository.findById(order.getId()).get();
        List<OderDetailResponse> oderDetail = oderDetailService.create(oderRequest.getLstProductDetail(),
                order.getId());
        Double gia = 0.0;
        for (OderDetailResponse x : oderDetail) {
            gia = gia + (x.getUnitprice() * x.getQuantity());
            if (order.getVoucher()!=null) {
                gia = gia / order.getVoucher().getDiscount();
            }
        }
        order.setTotal(gia);
        order = oderRepository.save(order);
        return ObjectMapperUtils.map(order, OderResponse.class);
    }

    public Boolean checkId(Integer id, Boolean checkDB) {
        if (id <= 0 || id == null) {
            return true;
        }
        if (!checkDB) { // check id db
            return true;
        }
        return false;
    }

    @Override
    public OderResponse findById(Integer id) {
        Order order = oderRepository.findById(id).get();
        OderResponse response = ObjectMapperUtils.map(order, OderResponse.class);
        for (OrderDetail x : order.getOrderDetail()) {
            for (OderDetailResponse y : response.getOrderDetail()) {
                y.getProductDetailId().getProduct().setProductImage(ObjectMapperUtils
                        .mapAll(x.getProductDetail().getProduct().getProductImage(), ProductImageResponse.class));
            }
        }
        return response;
    }
}
