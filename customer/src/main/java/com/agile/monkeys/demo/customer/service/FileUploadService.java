package com.agile.monkeys.demo.customer.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public interface FileUploadService {


    void saveFile(String id,
                         String fileName,
                         MultipartFile multipartFile);

    String getPhotoUrl(String id, String photo);
}
