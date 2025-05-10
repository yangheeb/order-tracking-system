package com.order.tracking.service;

import com.order.tracking.dto.CustomerRequestDto;
import com.order.tracking.entity.Customer;
import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerRequestDto customerRequestDto);
    Customer getCustomer(Long id);
    Customer getCustomerByEmail(String email);
    List<Customer> getAllCustomers();
    Customer updateCustomer(Long id, CustomerRequestDto customerRequestDto);
    void deleteCustomer(Long id);
    boolean existsByEmail(String email);
} 