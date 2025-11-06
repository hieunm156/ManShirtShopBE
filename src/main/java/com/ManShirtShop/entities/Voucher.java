package com.ManShirtShop.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ManShirtShop.entities.base.BaseEntity;

import lombok.Data;

@Entity
@Data
@Table(name = "voucher")
public class Voucher extends BaseEntity {


    @Column(length=255)
    private String name;
    @Column(length=255)
    private String code;
    @Column(length=255)
    private String description;
    @Column(name="start_date")
    private LocalDateTime startDate;
    @Column(name="end_date")
    private LocalDateTime endDate;
    private int discount;
    @OneToMany(mappedBy="voucher")
    private Set<Order> order;

}
