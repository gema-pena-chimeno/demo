package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CRUDDto {

    private String firstName;
    private String lastName;

    public Customer toCustomer(String id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);

        return customer;
    }
}
