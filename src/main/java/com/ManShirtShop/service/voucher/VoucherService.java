package com.ManShirtShop.service.voucher;

import com.ManShirtShop.dto.discount.DiscountRequest;
import com.ManShirtShop.dto.discount.DiscountResponse;
import com.ManShirtShop.dto.voucher.VoucherRequest;
import com.ManShirtShop.dto.voucher.VoucherResponse;

import java.util.List;

public interface VoucherService {
    List<VoucherResponse> getAll();
    VoucherResponse create(VoucherRequest request);
    VoucherResponse delete(Integer id);
    VoucherResponse findById(Integer id);
}
