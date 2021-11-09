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

    public Customer findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findByQuery(String query) {
        return customerRepository.findByQuery(query + "%");
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        Customer customerFromDb = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customerFromDb.setFirstName(customer.getFirstName());
        customerFromDb.setLastName(customer.getLastName());
        return customerRepository.save(customerFromDb);
    }

    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}
