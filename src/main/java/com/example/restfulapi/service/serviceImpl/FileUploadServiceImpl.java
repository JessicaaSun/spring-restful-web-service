package com.example.restfulapi.service.serviceImpl;

import com.example.restfulapi.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    Path fileLocationStorage;
    FileUploadServiceImpl(){
        fileLocationStorage = Paths.get("src/main/resources/images");
    }
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();
        if (filename != null) {
            // condition for filename
            if (filename.contains("..")) {
                System.out.println("Filename is incorrect !! ");
                return null;
            }
            String[] fileParts = filename.split("\\.");
            filename = UUID.randomUUID() + "." + fileParts[1];
            //resolved path

            Path resolvedPath = fileLocationStorage.resolve(filename);
            Files.copy(file.getInputStream(), resolvedPath, StandardCopyOption.REPLACE_EXISTING);
            return filename;

        } else return null;
    }}
