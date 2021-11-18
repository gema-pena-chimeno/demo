package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.customer.service.CustomerService;
import com.agile.monkeys.demo.customer.service.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api
@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    private static final String CREATE_DTO_FORMAT =
            "{\n" +
            "  \"firstName\":\"string\",\n" +
            "  \"lastName\":\"string\"\n" +
            "}";

    private static final String UPDATE_DTO_FORMAT =
            "{\n" +
            "  \"firstName\":\"string\",\n" +
            "  \"lastName\":\"string\",\n" +
            "  \"ignoreFile\":\"boolean\",\n" +
            "}";

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto create(@RequestParam(value = "photo", required = false) @ValidImageFile MultipartFile multipartFile,
                              @RequestPart("dto") @ApiParam(name = "dto", value = CREATE_DTO_FORMAT) @Valid CreateDto dto,
                              Principal principal) {

        return customerService.create(dto, multipartFile, principal.getName());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto update(@PathVariable(value = "id") String id,
                              @RequestParam(value = "photo", required = false) @ValidImageFile MultipartFile multipartFile,
                              @RequestPart("dto") @ApiParam(name = "dto", value = UPDATE_DTO_FORMAT) @Valid UpdateDto dto,
                              Principal principal) {

        return customerService.update(id, dto, multipartFile, principal.getName());
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") String id, Principal principal) {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
