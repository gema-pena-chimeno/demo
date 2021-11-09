package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.domain.Customer;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerService {

    List<Customer> findCustomerByCustomerNameStartingWith(String name); // fetch list of Customer which start with
    List<Customer> findCustomerByCustomerRole(String role);
    Customer findCustomerById(String id);
    List<Customer> findAll();

    @Transactional
    Customer save(Customer Customer);

    @Transactional
    void delete(long CustomerId);
}
