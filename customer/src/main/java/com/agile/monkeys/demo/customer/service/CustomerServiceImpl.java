package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.domain.Customer;
import com.agile.monkeys.demo.customer.domain.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private FileUploadService fileUploadService;

    public Customer findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findByQuery(String query) {
        return customerRepository.findByQuery(query + "%");
    }

    public Customer create(Customer customer, MultipartFile multipartFile) {

        String fileName = getFilenameFromMultipartFile(multipartFile);
        customer.setPhoto(fileName);

        Customer created = customerRepository.save(customer);

        if (customer.getPhoto() != null) {
            fileUploadService.saveFile(created.getId(), fileName, multipartFile);
        }

        return customerRepository.save(customer);
    }

    public Customer update(Customer customer, MultipartFile multipartFile) {
        Customer customerFromDb = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customerFromDb.setFirstName(customer.getFirstName());
        customerFromDb.setLastName(customer.getLastName());
        String fileName = getFilenameFromMultipartFile(multipartFile);
        customerFromDb.setPhoto(fileName);
        Customer updated = customerRepository.save(customerFromDb);

        if (customer.getPhoto() != null) {
            fileUploadService.saveFile(updated.getId(), fileName, multipartFile);
        }

        return updated;
    }

    public void delete(String id) {
        customerRepository.deleteById(id);
    }

    private String getFilenameFromMultipartFile(MultipartFile multipartFile) {
        return (multipartFile == null)
                ? null
                : StringUtils.cleanPath(multipartFile.getOriginalFilename());
    }

}
