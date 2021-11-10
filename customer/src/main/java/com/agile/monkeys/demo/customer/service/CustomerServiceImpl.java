package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.controller.CRUDDto;
import com.agile.monkeys.demo.customer.controller.CustomerDto;
import com.agile.monkeys.demo.customer.domain.Customer;
import com.agile.monkeys.demo.customer.domain.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private FileUploadService fileUploadService;

    public CustomerDto findCustomerById(String id) {
        Customer found = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return toCustomerDto(found);
    }

    public List<CustomerDto> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::toCustomerDto)
                .collect(Collectors.toList());
    }

    public List<CustomerDto> findByQuery(String query) {
        return customerRepository.findByQuery(query + "%")
                .stream()
                .map(this::toCustomerDto)
                .collect(Collectors.toList());
    }

    public CustomerDto create(CRUDDto dto, MultipartFile multipartFile) {
        String fileName = getFilenameFromMultipartFile(multipartFile);
        Customer customer = dto.toCustomer();
        customer.setPhoto(fileName);

        Customer created = customerRepository.save(customer);
        if (customer.getPhoto() != null) {
            fileUploadService.saveFile(created.getId(), fileName, multipartFile);
        }

        return toCustomerDto(created);
    }

    public CustomerDto update(String id, CRUDDto dto, MultipartFile multipartFile) {
        Customer customerFromDb = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customerFromDb.setFirstName(dto.getFirstName());
        customerFromDb.setLastName(dto.getLastName());
        String fileName = getFilenameFromMultipartFile(multipartFile);
        customerFromDb.setPhoto(fileName);
        Customer updated = customerRepository.save(customerFromDb);

        if (customerFromDb.getPhoto() != null) {
            fileUploadService.saveFile(updated.getId(), fileName, multipartFile);
        }

        return toCustomerDto(updated);
    }

    public void delete(String id) {
        customerRepository.deleteById(id);
    }

    private String getFilenameFromMultipartFile(MultipartFile multipartFile) {
        return (multipartFile == null)
                ? null
                : StringUtils.cleanPath(multipartFile.getOriginalFilename());
    }

    private CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto(customer, getPhotoUrl(customer));
    }

    private String getPhotoUrl(Customer customer) {
        if (customer.getPhoto() == null) {
            return null;
        }
        return fileUploadService.getPhotoUrl(customer.getId(), customer.getPhoto());
    }

}
