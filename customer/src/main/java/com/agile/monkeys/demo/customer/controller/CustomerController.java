package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.customer.service.CustomerService;
import com.agile.monkeys.demo.customer.service.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Api
@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    private static final String CRUD_DTO_FORMAT =
            "{\n" +
            "  \"firstName\":\"string\",\n" +
            "  \"lastName\":\"string\"\n" +
            "}";
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto create(@RequestParam(value = "photo", required = false) MultipartFile multipartFile,
                              @RequestPart("dto") @ApiParam(name = "dto", value = CRUD_DTO_FORMAT) @Valid CRUDDto dto,
                              Principal principal) {

        return customerService.create(dto, multipartFile, principal.getName());
    }

    // How to make a difference between no image or ignore image...
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto update(@PathVariable(value = "id") String id,
                              @RequestParam(value = "photo", required = false) MultipartFile multipartFile,
                              @RequestPart("dto") @ApiParam(name = "dto", value = CRUD_DTO_FORMAT) @Valid CRUDDto dto,
                              Principal principal) {

        return customerService.update(id, dto, multipartFile, principal.getName());
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") String id,
                       Principal principal) {
        customerService.delete(id, principal.getName());
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDto> list() {
        return customerService.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto getCustomer(@PathVariable("id") String id) {
        return customerService.findById(id);
    }

    @GetMapping(path = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDto> search(@PathVariable("query") String query) {
        return customerService.findByQuery(query);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
