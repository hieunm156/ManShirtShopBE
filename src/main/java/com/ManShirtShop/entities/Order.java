package com.ManShirtShop.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ManShirtShop.entities.base.BaseEntity;

import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order extends BaseEntity {

    private double freight;
    @Column(name="ship_name", length=50)
    private String shipName;
    @Column(length=255)
    private String address;
    @Column(name="city_name", length=50)
    private String cityName;
    @Column(name="district_name", length=50)
    private String districtName;
    @Column(name="ward_name", length=50)
    private String wardName;
    @Column(name="ship_phone", length=20)
    private String shipPhone;
    @Column(length=255)
    private String note;
    @Column(name="payment_type", length=255)
    private String paymentType;
    private double total;
    @Column(name="status_pay")
    private int statusPay;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    @OneToMany(mappedBy="order")
    private List<OrderDetail> orderDetail;
    @OneToMany(mappedBy="order")
    private List<Rating> rating;
    @ManyToOne
    @JoinColumn(name="voucher_id")
    private Voucher voucher;
}
