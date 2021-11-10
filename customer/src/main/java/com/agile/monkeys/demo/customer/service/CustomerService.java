package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.domain.Customer;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerService {

    Customer findCustomerById(String id);

    List<Customer> findByQuery(String query);

    List<Customer> findAll();

    @Transactional
    Customer create(Customer customer, MultipartFile multipartFile);

    @Transactional
    Customer update(Customer customer, MultipartFile multipartFile);

    @Transactional
    void delete(String id);
}
