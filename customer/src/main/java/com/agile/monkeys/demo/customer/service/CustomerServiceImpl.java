package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.domain.Customer;
import com.agile.monkeys.demo.customer.domain.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findCustomerByCustomerNameStartingWith(String name) {
        return null;

    } // fetch list of Customer which start with
    public List<Customer> findCustomerByCustomerRole(String role) {
        return null;

    }

    public Customer findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

    }
    // fetch Customer by role
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void delete(long CustomerId) {

    }
}
