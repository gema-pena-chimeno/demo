package com.agile.monkeys.demo.user.controller;

import com.agile.monkeys.demo.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Api
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private static final String CRUD_DTO_FORMAT =
            "{\n" +
                    "  \"firstName\":\"string\",\n" +
                    "  \"lastName\":\"string\"\n" +
                    "}";

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto create(@RequestBody @Valid CRUDDto dto) {

        return userService.create(dto);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto update(@PathVariable(value = "id") String id,
                              @RequestParam(value = "photo", required = false) MultipartFile multipartFile,
                              @RequestPart("dto") @ApiParam(name = "dto", value = CRUD_DTO_FORMAT) @Valid CRUDDto dto) {

        return userService.update(id, dto, multipartFile);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)        // or use @DeleteMapping
    public void delete(@PathVariable("id") String id){
        userService.delete(id);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> list() {
        return userService.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getCustomer(@PathVariable("id") String id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping(path = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> search(@PathVariable("query") String query) {
        return customerService.findByQuery(query);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
    }
}
