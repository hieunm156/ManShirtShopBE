package com.ManShirtShop.service.customer.Impl;

import com.ManShirtShop.common.mapperUtil.ObjectMapperUtils;
import com.ManShirtShop.dto.customer.CustomerRequest;
import com.ManShirtShop.dto.customer.CustomerResponse;
import com.ManShirtShop.dto.discount.DiscountResponse;
import com.ManShirtShop.entities.Customer;
import com.ManShirtShop.entities.Discount;
import com.ManShirtShop.entities.Role;
import com.ManShirtShop.repository.CustomerRepository;
import com.ManShirtShop.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public List<CustomerResponse> getAllActive() {
        List<Customer> geListDb = customerRepository.getAllActiveByStatus();
        return ObjectMapperUtils.mapAll(geListDb, CustomerResponse.class);
    }

    @Override
    public List<CustomerResponse> getAllDisActive() {
        List<Customer> geListDb = customerRepository.getAllDisActiveByStatus();
        return ObjectMapperUtils.mapAll(geListDb, CustomerResponse.class);
    }

    @Override
    public CustomerResponse create(CustomerRequest request) {
        request.setId(-1);
        Customer entity = ObjectMapperUtils.map(request, Customer.class);
        entity.setCreateBy("admin");
        Role role = new Role();
        role.setId(request.getRole());
        entity.setRole(role);
        entity.setBirthDate(request.getBirthDate());
        entity.setCreateTime(Timestamp.from(Instant.now()));
        entity = customerRepository.save(entity);
        return ObjectMapperUtils.map(entity, CustomerResponse.class);
    }

    public Boolean checkIb(Integer id) {
        if (id == 0 || id == null) {
            return false;
        }
        if (!customerRepository.existsById(id)) { // check id db
            return false;
        }
        return true;
    }
    @Override
    public CustomerResponse update(CustomerRequest request) {
        if (!checkIb(request.getId())) {
            return null;
        }
        Customer entityDB = customerRepository.findById(request.getId()).get();
        Customer entity = ObjectMapperUtils.map(request, Customer.class);
        entity.setCreateBy(entityDB.getCreateBy());
        entity.setCreateTime(entityDB.getCreateTime());
        entity.setBirthDate(entityDB.getBirthDate());
        entity.setUpdateBy("admin");
        Role role = new Role();
        role.setId(request.getRole());
        entity.setRole(role);
        entity.setUpdateTime(Timestamp.from(Instant.now()));
        entity = customerRepository.save(entity);
        return ObjectMapperUtils.map(entity, CustomerResponse.class);
    }

    @Override
    public CustomerResponse delete(Integer id) {
        if (!checkIb(id)) {
            return null;
        }
        Customer e = customerRepository.findById(id).get();
        e.setStatus(1);
        e = customerRepository.save(e);
        return ObjectMapperUtils.map(e, CustomerResponse.class);
    }

    @Override
    public CustomerResponse findById(Integer id) {
        if (!checkIb(id)) {
            return null;
        }
        Customer customer = customerRepository.findById(id).get();
        return ObjectMapperUtils.map(customer, CustomerResponse.class);
    }
}
