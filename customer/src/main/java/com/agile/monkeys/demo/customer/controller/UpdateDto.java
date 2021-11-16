package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.data.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDto extends CreateDto {

    // Set to true when the file parameter is sent as null and the current photo should not be changed.
    // Set to false when the file parameter can be null or a file, and this value override the current photo.
    private boolean ignoreFile;

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(this.getFirstName());
        customer.setLastName(this.getFirstName());
        customer.setActive(true);

        return customer;
    }
}
