package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.data.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CRUDDto {

    @Pattern(message="Type can contain alphabetical characters only", regexp = "[a-zA-Z ]+")
    private String firstName;

    @Pattern(message="Type can contain alphabetical characters only", regexp = "[a-zA-Z ]+")
    private String lastName;

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setActive(true);

        return customer;
    }
}
