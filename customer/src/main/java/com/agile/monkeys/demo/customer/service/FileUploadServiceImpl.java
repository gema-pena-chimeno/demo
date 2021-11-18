package com.agile.monkeys.demo.customer.service;

import java.io.*;
import java.nio.file.*;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Getter
    @Value("${customer.image.image_folder}")
    private String imageFolder;

    @Getter
    @Value("${customer.image.url_path}")
    private String urlPath;

    public void saveFile(String id,
                         String fileName,
                         MultipartFile multipartFile) {

        Path uploadPath = Paths.get(imageFolder + id);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            log.debug("File file {} saved in folder {}", fileName, uploadPath);
        } catch (IOException ioe) {
            log.error("Error saving file {}", fileName, ioe);
            throw new RuntimeException("Could not save image file: " + fileName, ioe);
        }
    }

    public String getPhotoUrl(String id, String photo) {
        if (photo == null || id == null) {
            return null;
        }

        return urlPath + "/" + id + "/" + photo;
    }
}
