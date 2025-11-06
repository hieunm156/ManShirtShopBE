package com.ManShirtShop.service.customer;

import com.ManShirtShop.dto.contact.ContactRequest;
import com.ManShirtShop.dto.contact.ContactResponse;
import com.ManShirtShop.dto.customer.CustomerRequest;
import com.ManShirtShop.dto.customer.CustomerResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getAllActive();
    List<CustomerResponse> getAllDisActive();
    CustomerResponse create(CustomerRequest request);

    CustomerResponse update(CustomerRequest request);

    CustomerResponse delete(Integer id);

    CustomerResponse findById(Integer id);
}
