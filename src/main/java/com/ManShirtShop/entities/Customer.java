// Generated with g9.

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ManShirtShop.entities.base.BaseEntity;

import lombok.Data;

@Entity
@Data
@Table(name = "customer")
public class Customer  extends BaseEntity {

    @Column(length=100)
    private String email;
    @Column(length=255)
    private String password;
    @Column(length=255)
    private String fullname;
    @Column(name="birth_date")
    private LocalDateTime birthDate;
    @Column(length=255)
    private String phone;
    @Column(length=255)
    private String photo;
    @OneToMany(mappedBy="customer")
    private Set<Address> address;
    @OneToMany(mappedBy="customer")
    private Set<Exchange> exchange;
    @OneToMany(mappedBy="customer")
    private Set<Order> order;
    @OneToMany(mappedBy="customer")
    private Set<Rating> rating;
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    // Manual getters and setters to resolve Lombok compilation issues
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }

    public Set<Exchange> getExchange() {
        return exchange;
    }

    public void setExchange(Set<Exchange> exchange) {
        this.exchange = exchange;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public Set<Rating> getRating() {
        return rating;
    }

    public void setRating(Set<Rating> rating) {
        this.rating = rating;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Getters for inherited BaseEntity fields
    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    @Override
    public Integer getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(Integer status) {
        super.setStatus(status);
    }
}
