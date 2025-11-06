package com.ManShirtShop.dto.client.oderDto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OderRequest {
    private Integer id;
    private double freight;
    private String shipName;
    private String address;
    private String cityName;
    private String districtName;
    private String wardName;
    private String shipPhone;
    private String note;
    private String paymentType;
    private double total;
    private int statusPay;
    private List<ProductDetailOderRequet> lstProductDetail;
    private Integer voucher;
}
