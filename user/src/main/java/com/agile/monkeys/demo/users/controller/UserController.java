package com.agile.monkeys.demo.users.controller;

import com.agile.monkeys.demo.data.UserRole;
import com.agile.monkeys.demo.users.service.LastAdminException;
import com.agile.monkeys.demo.users.service.NotFoundException;
import com.agile.monkeys.demo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto create(@RequestBody @ApiParam(name = "dto") @Valid CRUDDto dto) {

        return userService.create(dto);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto update(@PathVariable(value = "id") String id,
                          @RequestBody @ApiParam(name = "dto") @Valid CRUDDto dto) {

        return userService.update(id, dto);
    }

    @PutMapping(value = "{id}/updateRole/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateRole(@PathVariable(value = "id") String id,
                              @PathVariable(value = "role") UserRole role) {

        return userService.updateRole(id, role);
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
    public UserDto getUser(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @GetMapping(path = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> search(@PathVariable("query") String query) {
        return userService.findByQuery(query);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({LastAdminException.class})
    public ResponseEntity<Object> handleNotFoundException(LastAdminException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
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
