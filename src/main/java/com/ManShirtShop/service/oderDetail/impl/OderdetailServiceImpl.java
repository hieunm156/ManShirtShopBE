package com.ManShirtShop.service.oderDetail.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ManShirtShop.common.mapperUtil.ObjectMapperUtils;
import com.ManShirtShop.dto.client.oderDetailDto.OderDetailRequest;
import com.ManShirtShop.dto.client.oderDetailDto.OderDetailResponse;
import com.ManShirtShop.dto.client.oderDto.ProductDetailOderRequet;
import com.ManShirtShop.entities.Order;
import com.ManShirtShop.entities.OrderDetail;
import com.ManShirtShop.entities.ProductDetail;
import com.ManShirtShop.entities.Return;
import com.ManShirtShop.repository.OderRepository;
import com.ManShirtShop.repository.OrderDetailRepositoty;
import com.ManShirtShop.repository.ProductDetailRepository;
import com.ManShirtShop.repository.ReturnRepository;
import com.ManShirtShop.service.oderDetail.OderDetailService;

@Service
public class OderdetailServiceImpl implements OderDetailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OderdetailServiceImpl.class);
    @Autowired
    OrderDetailRepositoty orderDetailRepositoty;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    OderRepository oderRepository;

    @Autowired
    ReturnRepository returnRepository;

    @Override
    public List<OderDetailResponse> create(List<ProductDetailOderRequet> idProductDetail, Integer idOder) {
        Order order = new Order();
        if (checkId(idOder, oderRepository.existsById(idOder))) {
            LOGGER.error(OderdetailServiceImpl.class + ": Không tìm thấy id Oder" + idOder);
            return null;
        } else {
            order = oderRepository.findById(idOder).get();
        } // check order
        
        List<OrderDetail> lstEntity = new ArrayList<>();
        for (ProductDetailOderRequet x : idProductDetail) {
            OrderDetail entity = new OrderDetail();
            if (checkId(x.getId(), productDetailRepository.existsById(x.getId()))) {
                LOGGER.error(OderdetailServiceImpl.class + ": Không tìm thấy id Product Detail", idProductDetail);
            } else {
                ProductDetail productDetail = productDetailRepository.findById(x.getId()).get();
                entity.setReturnId(null);
                entity.setOrder(order);
                entity.setProductDetail(productDetail);
                entity.setCreateBy("KhachHang");
                entity.setCreateTime(Timestamp.from(Instant.now()));
                entity.setQuantity(x.getQuantity());
                entity.setUnitprice(productDetail.getProduct().getPrice());
                entity.setStatus(0);
                lstEntity.add(entity);
                orderDetailRepositoty.save(entity);
            }
        }
        lstEntity = orderDetailRepositoty.saveAll(lstEntity);
        return ObjectMapperUtils.mapAll(lstEntity, OderDetailResponse.class);
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
    public OderDetailResponse findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
