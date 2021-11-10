package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.customer.domain.Customer;
import com.agile.monkeys.demo.customer.service.CustomerService;
import com.agile.monkeys.demo.customer.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;
    private FileUploadService fileUploadService;

    private static final String CRUD_DTO_FORMAT =
            "{\n" +
                    "  \"firstName\":\"string\",\n" +
                    "  \"lastName\":\"string\"\n" +
                    "}";
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer save(@RequestParam(value = "photo", required = false) MultipartFile multipartFile,
                         @RequestPart("dto") @ApiParam(name = "dto", value = CRUD_DTO_FORMAT) @Valid CustomerDto customerDto) {

        return customerService.create(customerDto.toCustomer(), multipartFile);
    }

    @PutMapping(value = "{id}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable(value = "id") String id,
                       @RequestParam(value = "photo", required = false) MultipartFile multipartFile,
                       @RequestPart("dto") @ApiParam(name = "dto", value = CRUD_DTO_FORMAT) @Valid CustomerDto customerDto) {

        customerService.update(customerDto.toCustomer(), multipartFile);
    }

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)        // or use @DeleteMapping
    public void delete(@RequestParam("id") String id){
        customerService.delete(id);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDto> list() {

        return customerService.findAll()
                .stream()
                .map(customer -> new CustomerDto(
                        customer,
                        fileUploadService.getPhotoUrl(customer.getId(), customer.getPhoto())))
                .collect(Collectors.toList());
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
