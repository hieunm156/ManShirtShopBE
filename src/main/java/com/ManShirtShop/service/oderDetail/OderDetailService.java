package com.ManShirtShop.service.oderDetail;

import java.util.List;

import com.ManShirtShop.dto.client.oderDetailDto.OderDetailRequest;
import com.ManShirtShop.dto.client.oderDetailDto.OderDetailResponse;
import com.ManShirtShop.dto.client.oderDto.ProductDetailOderRequet;

public interface OderDetailService {
    List<OderDetailResponse> create(List<ProductDetailOderRequet> lstRequest,Integer inOder);

    OderDetailResponse findById(Integer id);
}
