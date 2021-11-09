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
    
//    // to add new customer
//    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/create"  )
    public Customer save(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> listCustomer() {
        return customerService.findAll();
    }

//    //@GetMapping(path = "/{customerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Customer getCustomer(@PathVariable("customerId") String customerId) {
//        return customerService.findCustomerById(customerId);
//    }
//
//    // delete specific customer using customer id
//    @RequestMapping(value = "delete", method = RequestMethod.DELETE)        // or use @DeleteMapping
//    public void delete(@RequestParam("id")long id){
//        customerService.delete(id);
//    }
//    // search customer start with name
//    @RequestMapping(value = "startWithName/{name}")
//    public List<Customer> findByName(@PathVariable("name")String name){
//        return customerService.findCustomerByCustomerNameStartingWith(name);
//    }
//    // search customer by role
//    @RequestMapping(value = "findByRole/{role}")
//    public List<Customer> findByRole(@PathVariable("role")String role){
//        return customerService.findCustomerByCustomerRole(role);
//    }
}
