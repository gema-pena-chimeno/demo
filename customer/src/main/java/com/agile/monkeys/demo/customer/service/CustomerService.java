package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.controller.CRUDDto;
import com.agile.monkeys.demo.customer.controller.CustomerDto;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerService {

    CustomerDto findById(String id);

    List<CustomerDto> findByQuery(String query);

    List<CustomerDto> findAll();

    @Transactional
    CustomerDto create(CRUDDto dto, MultipartFile multipartFile, String userName);

    @Transactional
    CustomerDto update(String id, CRUDDto dto, MultipartFile multipartFile, String userName);

    @Transactional
    void delete(String id, String userName);
}
