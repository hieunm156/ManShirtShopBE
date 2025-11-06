package com.ManShirtShop.service.voucher.impl;

import com.ManShirtShop.common.mapperUtil.ObjectMapperUtils;
import com.ManShirtShop.dto.discount.DiscountResponse;
import com.ManShirtShop.dto.material_Dto.ResponseMaterial;
import com.ManShirtShop.dto.voucher.VoucherRequest;
import com.ManShirtShop.dto.voucher.VoucherResponse;
import com.ManShirtShop.entities.Discount;
import com.ManShirtShop.entities.Material;
import com.ManShirtShop.entities.Voucher;
import com.ManShirtShop.repository.VoucherRepository;
import com.ManShirtShop.service.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@Service
public class VoucherImpl implements VoucherService {
    @Autowired
    VoucherRepository voucherRepository;


    @Override
    public List<VoucherResponse> getAll() {
        List<VoucherResponse> listAll = ObjectMapperUtils.mapAll(voucherRepository.findAll(), VoucherResponse.class)
                .stream().filter(e->e.getStatus()==0).toList();;
        return listAll;
    }

    @Override
    public VoucherResponse create(VoucherRequest request) {
        request.setId(null);
        if (request.getStartDate().isAfter(request.getEndDate()) || // nếu end date nhỏ hơn startdate => null
                request.getStartDate().equals(request.getEndDate())) { // hoặc bằng start => null
            System.out.println("---------aaaaaaaaaaaa--------");
            return null;
        }
        String giftCode = generateRandomCode();
        while (isGiftCodeExists(giftCode)) {
            giftCode = generateRandomCode();
        }
        Voucher entity = ObjectMapperUtils.map(request, Voucher.class);
        entity.setCreateBy("admin");
        entity.setCreateTime(Timestamp.from(Instant.now()));
        entity.setStatus(0);
        entity.setCode(giftCode);
        entity = voucherRepository.save(entity);
        return ObjectMapperUtils.map(entity, VoucherResponse.class);
    }



    @Override
    public VoucherResponse delete(Integer id) {
        if (!checkIb(id)) {
            return null;
        }
        Voucher e = voucherRepository.findById(id).get();
        e.setStatus(1);
        e = voucherRepository.save(e);
        return ObjectMapperUtils.map(e, VoucherResponse.class);
    }

    @Override
    public VoucherResponse findById(Integer id) {
        if (!checkIb(id)) {
            return null;
        }
        Voucher voucher = voucherRepository.findById(id).get();
        return ObjectMapperUtils.map(voucher, VoucherResponse.class);
    }
    public Boolean checkIb(Integer id) {
        if (id == 0 || id == null) {
            return false;
        }
        if (!voucherRepository.existsById(id)) { // check id db
            return false;
        }
        return true;
    }
    // Generate random GiftCode
    private String generateRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            code.append(chars.charAt(index));
        }
        return code.toString();
    }

    public boolean isGiftCodeExists(String giftCode) {
        return voucherRepository.existsByCode(giftCode);
    }
}
