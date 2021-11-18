package com.agile.monkeys.demo.customer.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class ImageValidatorTest {

    ImageValidator imageValidator = new ImageValidator();

    @Test
    public void isValid_nullMultipartFile_returnTrue() {
        assertTrue(imageValidator.isValid(null, null));
    }

    @Test
    public void isValid_emptyMultipartFile_returnTrue() {
        byte[] bytes = new byte[0];
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "fileName.png",
                "fileName.png",
                "image/png",
                bytes);
        assertTrue(imageValidator.isValid(mockMultipartFile, null));
    }

    @Test
    public void isValid_pngMultipartFile_returnTrue() {
        byte[] bytes = "mocked image content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.png","image/png", bytes);

        assertTrue(imageValidator.isValid(mockMultipartFile, null));
    }

    @Test
    public void isValid_jpgMultipartFile_returnTrue() {
        byte[] bytes = "mocked image content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.jpg","image/jpg", bytes);

        assertTrue(imageValidator.isValid(mockMultipartFile, null));
    }

    @Test
    public void isValid_jpegMultipartFile_returnTrue() {
        byte[] bytes = "mocked image content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.jpg","image/jpeg", bytes);

        assertTrue(imageValidator.isValid(mockMultipartFile, null));
    }

    @Test
    public void isValid_gifMultipartFile_returnFalse() {
        byte[] bytes = "mocked image content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.gif","image/gif", bytes);

        assertFalse(imageValidator.isValid(mockMultipartFile, null));
    }

    @Test
    public void isValid_textMultipartFile_returnFalse() {
        byte[] bytes = "mocked content".getBytes();
        MockMultipartFile mockMultipartFile = generateMockMultipartFile("fileName.txt","image/txt", bytes);

        assertFalse(imageValidator.isValid(mockMultipartFile, null));
    }

    private MockMultipartFile generateMockMultipartFile(String fileName, String mimeType, byte[] bytes) {
        return new MockMultipartFile(fileName, fileName, mimeType, bytes);
    }

}