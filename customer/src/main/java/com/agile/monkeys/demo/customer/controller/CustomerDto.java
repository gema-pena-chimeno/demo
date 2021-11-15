package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.data.Customer;
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

    public CustomerDto(Customer customer, String photoUrl) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.photoUrl = photoUrl;
    }
}
