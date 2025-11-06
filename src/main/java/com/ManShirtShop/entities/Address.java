package com.ManShirtShop.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ManShirtShop.entities.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adress")
public class Address extends BaseEntity  {

    @Column(length=255)
    private String address;
    @Column(name="city_name", length=50)
    private String cityName;
    @Column(name="district_name", length=50)
    private String districtName;
    @Column(name="ward_name", length=50)
    private String wardName;
    @Column(length=255)
    private String other;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

}
