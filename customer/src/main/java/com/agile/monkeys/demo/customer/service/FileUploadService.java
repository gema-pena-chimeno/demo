package com.agile.monkeys.demo.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileUploadService {

    void saveFile(String id, String fileName, MultipartFile multipartFile);

    String getPhotoUrl(String id, String photo);
}
