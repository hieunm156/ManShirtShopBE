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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ManShirtShop.entities.base.BaseEntity;

import lombok.Data;

@Entity
@Data
@Table(name = "returns")
public class Return extends BaseEntity {

    @Column(length=255)
    private String video;
    @Column(length=255)
    private String reason;
    @Column(length=255)
    private String note;
    private double total;
    @OneToMany(mappedBy="returnId")
    private Set<Exchange> exchange;
    @OneToMany(mappedBy="returnId")
    private List<OrderDetail> orderDetail;

}
