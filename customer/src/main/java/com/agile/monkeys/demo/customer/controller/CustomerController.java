package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.customer.domain.Customer;
import com.agile.monkeys.demo.customer.service.CustomerService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;
    
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer save(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)        // or use @DeleteMapping
    public void update(@RequestBody Customer customer){
        customerService.update(customer);
    }

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)        // or use @DeleteMapping
    public void delete(@RequestParam("id") String id){
        customerService.delete(id);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> listCustomer() {
        return customerService.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("id") String id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping(path = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> search(@PathVariable("query") String query) {
        return customerService.findByQuery(query);
    }
}
