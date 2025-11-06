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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail extends BaseEntity {
    private int quantity; // số lượng
    private double unitprice; // giá
    @Column(name="return_quantity")
    private int returnQuantity; //số lượng trả
    @Column(length=255)
    private String note;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name="product_detail_id")
    private ProductDetail productDetail;
    @ManyToOne
    @JoinColumn(name="returnId")
    private Return returnId;
}
