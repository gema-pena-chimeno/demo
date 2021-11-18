package com.agile.monkeys.demo.customer.service;

import com.agile.monkeys.demo.customer.controller.UpdateDto;
import com.agile.monkeys.demo.data.Customer;
import com.agile.monkeys.demo.customer.controller.CreateDto;
import com.agile.monkeys.demo.customer.controller.CustomerDto;
import com.agile.monkeys.demo.customer.domain.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private FileUploadService fileUploadService;

    public CustomerDto findById(String id) {
        return toCustomerDto(findCustomer(id));
    }

    public List<CustomerDto> findAll() {
        return customerRepository.findAll()
                .stream()
                .filter(Customer::isActive)
                .map(this::toCustomerDto)
                .collect(Collectors.toList());
    }

    public List<CustomerDto> findByQuery(String query) {
        return customerRepository.findByQuery(query + "%")
                .stream()
                .map(this::toCustomerDto)
                .collect(Collectors.toList());
    }

    public CustomerDto create(CreateDto dto, MultipartFile multipartFile, String userName) {
        String fileName = getFilenameFromMultipartFile(multipartFile);
        Customer customer = dto.toCustomer();
        customer.setPhoto(fileName);
        customer.setCreatedBy(userName);

        Customer created = customerRepository.save(customer);
        if (customer.getPhoto() != null) {
            fileUploadService.saveFile(created.getId(), fileName, multipartFile);
        }

        log.info("Customer {} ({} {}) created", created.getId(), created.getFirstName(), created.getLastName());
        return toCustomerDto(created);
    }

    public CustomerDto update(String id, UpdateDto dto, MultipartFile multipartFile, String userName) {
        Customer customerFromDb = findCustomer(id);

        customerFromDb.setFirstName(dto.getFirstName());
        customerFromDb.setLastName(dto.getLastName());
        customerFromDb.setUpdateBy(userName);

        String fileName = null;
        if (!dto.isIgnoreFile()) {
            fileName = getFilenameFromMultipartFile(multipartFile);
            customerFromDb.setPhoto(fileName);
        }

        Customer updated = customerRepository.save(customerFromDb);

        if (fileName != null) {
            fileUploadService.saveFile(updated.getId(), fileName, multipartFile);
        }

        log.info("Customer {} ({} {}) update", updated.getId(), updated.getFirstName(), updated.getLastName());
        return toCustomerDto(updated);
    }

    public void delete(String id, String userName) {
        Customer customer = findCustomer(id);

        customer.setActive(false);
        customer.setUpdateBy(userName);
        customerRepository.save(customer);
        log.info("Customer {} ({} {}) deleted", customer.getId(), customer.getFirstName(), customer.getLastName());
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

    private Customer findCustomer(String id) {
        Customer found = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        if (!found.isActive()) {
            throw new NotFoundException("Customer not found");
        }

        return found;
    }
}
