package com.ManShirtShop.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
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

import lombok.Data;

@Entity
@Data
@Table(name = "exchange_detail")
public class ExchangeDetail extends BaseEntity {

    private int quantity;
    private double unitprice;
    private int note;
    @ManyToOne
    @JoinColumn(name="exchange_id")
    private Exchange exchange;
    @ManyToOne
    @JoinColumn(name="product_detail_id")
    private ProductDetail productDetail;

}
