package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.domain.Customer;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerService {

    Customer findCustomerById(String id);

    List<Customer> findByQuery(String query);

    List<Customer> findAll();

    @Transactional
    Customer save(Customer Customer);

    @Transactional
    Customer update(Customer Customer);

    @Transactional
    void delete(String id);
}
