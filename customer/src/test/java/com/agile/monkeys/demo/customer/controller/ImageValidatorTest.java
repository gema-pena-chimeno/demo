package com.agile.monkeys.demo.customer.controller;

import com.agile.monkeys.demo.customer.service.ImageValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ImageValidatorTest {

    @InjectMocks
    private ImageValidator imageValidator;

    @Test
    public void isValid_nullMultipartFile_validated() {
        imageValidator.validate(null);
    }

    @Test
    public void isValid_emptyMultipartFile_validated() {
        byte[] bytes = new byte[0];
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "fileName.png",
                "fileName.png",
                "image/png",
                bytes);

        imageValidator.validate(mockMultipartFile);
    }

    @Test
    public void isValid_pngMultipartFile_validated() {
        byte[] bytes = "mocked image content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.png","image/png", bytes);

        imageValidator.validate(mockMultipartFile);
    }

    @Test
    public void isValid_jpgMultipartFile_validated() {
        byte[] bytes = "mocked image content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.jpg","image/jpg", bytes);

        imageValidator.validate(mockMultipartFile);
    }

    @Test
    public void isValid_jpegMultipartFile_validated() {
        byte[] bytes = "mocked image content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.jpeg","image/jpeg", bytes);

        imageValidator.validate(mockMultipartFile);
    }

    @Test
    public void isValid_gifMultipartFile_throwInvalidMediaTypeException() {
        byte[] bytes = "mocked image content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.gif","image/gif", bytes);

        assertThrows(InvalidMediaTypeException.class, () -> imageValidator.validate(mockMultipartFile));
    }

    @Test
    public void isValid_textMultipartFile_throwInvalidMediaTypeException() {
        byte[] bytes = "mocked content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.txt","image/txt", bytes);

        assertThrows(InvalidMediaTypeException.class, () -> imageValidator.validate(mockMultipartFile));
    }

    private MockMultipartFile generateMockMultipartFile(String fileName, String mimeType, byte[] bytes) {
        return new MockMultipartFile(fileName, fileName, mimeType, bytes);
    }

}