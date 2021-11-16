package com.agile.monkeys.demo.customer;

import com.agile.monkeys.demo.customer.controller.CreateDto;
import com.agile.monkeys.demo.customer.controller.CustomerController;
import com.agile.monkeys.demo.customer.controller.CustomerDto;
import com.agile.monkeys.demo.customer.controller.UpdateDto;
import com.agile.monkeys.demo.customer.domain.CustomerRepository;
import com.agile.monkeys.demo.customer.service.NotFoundException;
import com.agile.monkeys.demo.customer.utils.ResourceUtils;
import com.agile.monkeys.demo.data.Customer;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Note that this test cannot test:
 * - Spring security configuration to set access over pages.
 * - Format validation on the parameters of the REST API.
 */
@RunWith(SpringRunner.class)
public class CustomerAPiIT extends SpringBase {

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // These values are the same that the values set in application.yml in the profile test
    private static final String URL_PATH = "/image-data";
    private static final String IMAGE_FOLDER = "./";

    @BeforeAll
    public void init() {
        configureUsers();
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void create_withAdminAndWithoutFile_customerCreated() {
        // given
        String userName = "admin";
        String firstName = "Charles";
        String lastName = "Dickens";
        CreateDto dto = generateCreateDto(firstName, lastName);
        Principal principal = generatePrincipal(userName);

        // when
        final CustomerDto result = customerController.create(null, dto, principal);

        // then
        assertCustomerDto(result, firstName, lastName, null);
        assertCustomerInDb(result.getId(), firstName, lastName, null, userName, null, true);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void create_withUserActiveAndWithFile_customerCreated() {
        // given
        String userName = "user_active";
        String fileName = "image.png";
        String firstName = "Naomi";
        String lastName = "Wolf";
        CreateDto dto = generateCreateDto(firstName, lastName);
        Principal principal = generatePrincipal(userName);
        MultipartFile file = generateMultipartFile(fileName, "image/png");

        // when
        final CustomerDto result = customerController.create(file, dto, principal);

        // then
        assertCustomerDto(result, firstName, lastName, fileName);
        assertCustomerInDb(result.getId(), firstName, lastName, fileName, userName, null, true);
        assertFileInFileSystem(result.getId(), fileName);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void update_removePhoto_customerUpdated() {
        // given
        String creationUserName = "admin";
        String fileName = "image.jpg";
        String firstName = "Mikel";
        String lastName = "Cervantes";
        CreateDto dto = generateCreateDto(firstName, lastName);
        Principal principal = generatePrincipal(creationUserName);
        MultipartFile file = generateMultipartFile(fileName, "image/png");
        final CustomerDto createdCustomer = customerController.create(file, dto, principal);
        assertNotNull(createdCustomer.getPhotoUrl());

        String updateUserName = "user_active";
        String newFirstName = "Miguel";
        String newLastName = "de Cervantes";
        UpdateDto updateDto = generateUpdatedDto(newFirstName, newLastName, false);
        Principal updatePrincipal = generatePrincipal(updateUserName);

        // when
        final CustomerDto result = customerController.update(
                createdCustomer.getId(),
                null,
                updateDto,
                updatePrincipal);

        // then
        assertCustomerDto(result, newFirstName, newLastName, null);
        assertCustomerInDb(result.getId(), newFirstName, newLastName, null, creationUserName, updateUserName, true);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void update_replacePhoto_customerUpdated() {
        // given
        String creationUserName = "user_active";
        String fileName = "image.jpg";
        String firstName = "Sara";
        String lastName = "Conor";
        CreateDto dto = generateCreateDto(firstName, lastName);
        Principal principal = generatePrincipal(creationUserName);
        MultipartFile file = generateMultipartFile(fileName, "image/jpeg");
        final CustomerDto createdCustomer = customerController.create(file, dto, principal);
        assertNotNull(createdCustomer.getPhotoUrl());

        String updateUserName = "admin";
        String newFileName = "image.png";
        String newFirstName = "Sarah";
        String newLastName = "Connor";
        UpdateDto updateDto = generateUpdatedDto(newFirstName, newLastName, false);
        Principal updatePrincipal = generatePrincipal(updateUserName);
        MultipartFile newFile = generateMultipartFile(newFileName, "image/png");

        // when
        final CustomerDto result = customerController.update(
                createdCustomer.getId(),
                newFile,
                updateDto,
                updatePrincipal);

        // then
        assertCustomerDto(result, newFirstName, newLastName, newFileName);
        assertCustomerInDb(result.getId(), newFirstName, newLastName, newFileName, creationUserName, updateUserName, true);
        assertFileInFileSystem(result.getId(), newFileName);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void update_addPhoto_customerUpdated() {
        // given
        String creationUserName = "user_active";
        String firstName = "Sara";
        String lastName = "Conor";
        CreateDto dto = generateCreateDto(firstName, lastName);
        Principal principal = generatePrincipal(creationUserName);
        final CustomerDto createdCustomer = customerController.create(null, dto, principal);
        assertNull(createdCustomer.getPhotoUrl());

        String updateUserName = "admin";
        String newFileName = "image.png";
        String newFirstName = "Sarah";
        String newLastName = "Connor";
        UpdateDto updateDto = generateUpdatedDto(newFirstName, newLastName, false);
        Principal updatePrincipal = generatePrincipal(updateUserName);
        MultipartFile newFile = generateMultipartFile(newFileName, "image/png");

        // when
        final CustomerDto result = customerController.update(
                createdCustomer.getId(),
                newFile,
                updateDto,
                updatePrincipal);

        // then
        assertCustomerDto(result, newFirstName, newLastName, newFileName);
        assertCustomerInDb(result.getId(), newFirstName, newLastName, newFileName, creationUserName, updateUserName, true);
        assertFileInFileSystem(result.getId(), newFileName);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void update_ignorePhoto_customerUpdated() {
        // given
        String creationUserName = "user_active";
        String fileName = "image.jpg";
        String firstName = "Sara";
        String lastName = "Conor";
        CreateDto dto = generateCreateDto(firstName, lastName);
        Principal principal = generatePrincipal(creationUserName);
        MultipartFile file = generateMultipartFile(fileName, "image/jpeg");
        final CustomerDto createdCustomer = customerController.create(file, dto, principal);
        assertNotNull(createdCustomer.getPhotoUrl());

        String updateUserName = "admin";
        String newFirstName = "Sarah";
        String newLastName = "Connor";
        UpdateDto updateDto = generateUpdatedDto(newFirstName, newLastName, true);
        Principal updatePrincipal = generatePrincipal(updateUserName);

        // when
        final CustomerDto result = customerController.update(
                createdCustomer.getId(),
                null,
                updateDto,
                updatePrincipal);

        // then
        assertCustomerDto(result, newFirstName, newLastName, fileName);
        assertCustomerInDb(result.getId(), newFirstName, newLastName, fileName, creationUserName, updateUserName, true);
        assertFileInFileSystem(result.getId(), fileName);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void update_notExistingCustomer_notFoundException() {
        // given
        String updateUserName = "admin";
        String firstName = "Sarah";
        String lastName = "Connor";
        UpdateDto updateDto = generateUpdatedDto(firstName, lastName, false);
        Principal updatePrincipal = generatePrincipal(updateUserName);

        // when / then
        assertThrows(NotFoundException.class, () -> customerController.update(
                "unexisting-customer-id",
                null,
                updateDto,
                updatePrincipal));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void delete_existingCustomer_customerInactive() {
        // given
        String creationUserName = "user_active";
        String firstName = "Michael";
        String lastName = "Conor";
        CreateDto dto = generateCreateDto(firstName, lastName);
        Principal principal = generatePrincipal(creationUserName);
        final CustomerDto createdCustomer = customerController.create(null, dto, principal);
        assertCustomerInDb(createdCustomer.getId(), firstName, lastName, null, creationUserName, null, true);

        String deleteUserName = "admin";
        Principal deletePrincipal = generatePrincipal(deleteUserName);

        // when
        customerController.delete(createdCustomer.getId(), deletePrincipal);

        // then
        assertCustomerInDb(createdCustomer.getId(), firstName, lastName, null, creationUserName, deleteUserName, false);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void delete_notExistingUser_notFoundException() {
        // given
        String creationUserName = "user_active";
        String fileName = "image.jpg";
        String firstName = "Sara";
        String lastName = "Conor";
        CreateDto dto = generateCreateDto(firstName, lastName);
        Principal principal = generatePrincipal(creationUserName);
        MultipartFile file = generateMultipartFile(fileName, "image/jpeg");
        final CustomerDto createdCustomer = customerController.create(file, dto, principal);
        assertCustomerInDb(createdCustomer.getId(), firstName, lastName, fileName, creationUserName, null, true);

        String deleteUserName = "admin";
        Principal deletePrincipal = generatePrincipal(deleteUserName);

        // when / then
        assertThrows(NotFoundException.class, () -> customerController.delete("unexisting-customer-id", deletePrincipal));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void getCustomer_existingCustomer_customerReturned() {
        // given
        String creationUserName = "user_active";
        String firstName = "Michael";
        String lastName = "Conor";
        CreateDto dto = generateCreateDto(firstName, lastName);
        Principal principal = generatePrincipal(creationUserName);
        final CustomerDto createdCustomer = customerController.create(null, dto, principal);
        assertCustomerInDb(createdCustomer.getId(), firstName, lastName, null, creationUserName, null, true);

        // when
        CustomerDto result = customerController.getCustomer(createdCustomer.getId());

        // then
        assertCustomerDto(result, firstName, lastName, null);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void list_existingCustomers_customersListed() {
        // given
        String creationUserName = "user_active";
        Principal principal = generatePrincipal(creationUserName);
        String fileName = "image.jpg";
        String firstName1 = "Michael";
        String lastName1 = "Jackson";
        CreateDto dto1 = generateCreateDto(firstName1, lastName1);
        final CustomerDto createdCustomer1 = customerController.create(null, dto1, principal);
        String firstName2 = "Robin";
        String lastName2 = "Hood";
        CreateDto dto2 = generateCreateDto(firstName2, lastName2);
        MultipartFile file = generateMultipartFile(fileName, "image/jpeg");
        final CustomerDto createdCustomer2 = customerController.create(file, dto2, principal);
        String firstName3 = "Carl";
        String lastName3 = "Hills";
        CreateDto dto3 = generateCreateDto(firstName3, lastName3);
        final CustomerDto createdCustomer3 = customerController.create(null, dto3, principal);
        customerController.delete(createdCustomer3.getId(), principal);

        // when
        List<CustomerDto> result = customerController.list();

        // then
        List<String> returnedIds = result.stream().map(CustomerDto::getId).collect(Collectors.toList());
        assertTrue(returnedIds.containsAll(List.of(createdCustomer1.getId(), createdCustomer2.getId())));
        assertFalse(returnedIds.contains(createdCustomer3.getId()));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void search_foundByQuery_customerReturned() {
        // given
        String creationUserName = "user_active";
        Principal principal = generatePrincipal(creationUserName);
        CreateDto dto1 = generateCreateDto("Peter", "Fox");
        customerController.create(null, dto1, principal);
        CreateDto dto2 = generateCreateDto("Peter", "Harris");
        customerController.create(null, dto2, principal);
        CreateDto dto3 = generateCreateDto("Olga", "Smith");
        customerController.create(null, dto3, principal);
        CreateDto dto4 = generateCreateDto("Harry", "Peterson");
        customerController.create(null, dto4, principal);

        CreateDto dto5 = generateCreateDto("Peter", "Smith");
        final CustomerDto createdCustomer = customerController.create(null, dto5, principal);
        customerController.delete(createdCustomer.getId(), principal);

        // when/ then
        List<CustomerDto> result1 = customerController.search("peter");
        assertEquals(3, result1.size());

        List<CustomerDto> result2 = customerController.search("harr");
        assertEquals(2, result2.size());

        List<CustomerDto> result3 = customerController.search("fox");
        assertEquals(1, result3.size());

        List<CustomerDto> result4 = customerController.search("Smi");
        assertEquals(1, result4.size());
    }

    private void configureUsers() {
        String query = ResourceUtils.loadAsString("sql_scripts/insert_users.sql");
        jdbcTemplate.execute(query);
    }

    private CreateDto generateCreateDto(String firstName, String lastName) {
        CreateDto dto = new CreateDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);

        return dto;
    }

    private UpdateDto generateUpdatedDto(String firstName, String lastName, boolean ignoreFile) {
        UpdateDto dto = new UpdateDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setIgnoreFile(ignoreFile);

        return dto;
    }

    private Principal generatePrincipal(String userName) {
        return new Principal() {
            @Override
            public String getName() {
                return userName;
            }
        };
    }

    private MultipartFile generateMultipartFile(String fileName, String mimeType) {
        try {
            File file = ResourceUtils.loadAsFile("test_files/" + fileName);
            FileInputStream input = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), mimeType, IOUtils.toByteArray(input));
            return multipartFile;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void assertCustomerDto(CustomerDto customerDto, String firstName, String lastName, String fileName) {
        assertAll(
                () -> assertNotNull(customerDto),
                () -> assertEquals(firstName, customerDto.getFirstName()),
                () -> assertEquals(lastName, customerDto.getLastName()),
                () -> assertFileName(customerDto, fileName));
    }

    private void assertFileName(CustomerDto customerDto, String fileName) {
        if (fileName == null) {
            assertNull(customerDto.getPhotoUrl());
        } else {
            assertEquals(URL_PATH + "/" + customerDto.getId() + "/" + fileName, customerDto.getPhotoUrl());
        }
    }

    private void assertCustomerInDb(String id,
                                    String firstName,
                                    String lastName,
                                    String fileName,
                                    String createdBy,
                                    String updatedBy,
                                    boolean active) {
        Optional<Customer> customer = customerRepository.findById(id);
        assertAll(
                () -> assertTrue(customer.isPresent()),
                () -> assertEquals(firstName, customer.get().getFirstName()),
                () -> assertEquals(lastName, customer.get().getLastName()),
                () -> assertEquals(createdBy, customer.get().getCreatedBy()),
                () -> assertEquals(fileName, customer.get().getPhoto()),
                () -> assertEquals(updatedBy, customer.get().getUpdateBy()),
                () -> assertEquals(active, customer.get().isActive()));

    }

    private void assertFileInFileSystem(String id, String fileName) {
        File savedPhoto = new File(IMAGE_FOLDER + id + "/" + fileName);
        assertTrue(savedPhoto.exists());
    }
}


