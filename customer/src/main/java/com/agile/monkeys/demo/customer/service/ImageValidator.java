package com.agile.monkeys.demo.customer.service;

import org.springframework.http.InvalidMediaTypeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageValidator {

    private static final List<String> SUPPORTED_TYPES = List.of("jpg", "jpeg", "png");

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getSize() == 0) {
            return;
        }

        boolean typeFound = SUPPORTED_TYPES
                .stream()
                .filter(type -> checkExtensionAndMimeType(file.getName(), file.getContentType(), type))
                .count() > 0;

        if (!typeFound) {
            throw new InvalidMediaTypeException(file.getContentType(), "File type not supported");
        }
    }

    private boolean checkExtensionAndMimeType(String fileName, String mimeType, String fileType) {
        return (fileName.endsWith("." + fileType) && mimeType.toLowerCase().equals("image/" + fileType));
    }
}
