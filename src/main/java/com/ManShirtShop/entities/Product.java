package com.ManShirtShop.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
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
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "product")
public class Product extends BaseEntity {

    @Column(length = 255)
    private String name;
    private double price;
    @Column(length = 255)
    private String description;
    private double weight;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product")
    private List<ProductDetail> productDetail;
    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImage;
    @OneToMany(mappedBy = "product")
    private List<Rating> rating;
    @ManyToOne
    @JoinColumn(name = "design_id")
    private Design design;
    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    @ManyToOne
    @JoinColumn(name = "sleeve_id")
    private Sleeve sleeve;
    @ManyToOne
    @JoinColumn(name = "collar_id")
    private Collar collar;
    @OneToMany(mappedBy = "productId")
    private List<ProductDiscount> productDiscount;

}
