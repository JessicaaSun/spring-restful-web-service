package com.example.restfulapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileUploadService {
    String uploadFile(MultipartFile file);
    String deleteFileByName(String filename);
    String deleteAllFiles();
}
