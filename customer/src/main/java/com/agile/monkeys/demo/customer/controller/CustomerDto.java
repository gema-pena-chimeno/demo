package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.customer.domain.Customer;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String id;

    private String firstName;

    private String lastName;

    private String photoUrl;

    // TODO: remove?
    public CustomerDto(Customer customer, String photoUrl) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.photoUrl = photoUrl;
    }

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);

        return customer;
    }
}
