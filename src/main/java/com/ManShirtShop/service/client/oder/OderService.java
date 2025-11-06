package com.ManShirtShop.service.client.oder;

import com.ManShirtShop.dto.client.oderDto.OderRequest;
import com.ManShirtShop.dto.client.oderDto.OderResponse;

public interface OderService {
    OderResponse create(OderRequest oderRequest);
    OderResponse findById(Integer id);
}
