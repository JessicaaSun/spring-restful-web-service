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
    private final String serverLocation = "src/main/resources/images";
    Path fileLocationStorage;
    FileUploadServiceImpl(){
        fileLocationStorage = Paths.get(serverLocation);
        try{
            if(!Files.exists(fileLocationStorage)){
                Files.createDirectories(fileLocationStorage);
            } else {
                System.out.println("Directory is already exist!");
            }
        } catch (Exception ex){
            System.out.println("Error creating directory: "+ex.getMessage());
        }

    }

//    @Override
//    public String uploadFile(String path, MultipartFile file) throws IOException {
//
//        String filename = file.getOriginalFilename();
//        if (filename != null) {
//            // condition for filename
//            if (filename.contains("..")) {
//                System.out.println("Filename is incorrect !! ");
//                return null;
//            }
//            String[] fileParts = filename.split("\\.");
//            filename = UUID.randomUUID() + "." + fileParts[1];
//            //resolved path
//
//            Path resolvedPath = fileLocationStorage.resolve(filename);
//            Files.copy(file.getInputStream(), resolvedPath, StandardCopyOption.REPLACE_EXISTING);
//            return filename;
//
//        } else return null;
//    }

    @Override
    public String uploadFile(MultipartFile file){
        //format fiename;
        String filename = file.getOriginalFilename();
        //check to see if file is empty
        String[] fileCompartments = filename.split("\\.");
        // String[]
        // [0] = 7328nehdfr4-3829suj
        // [1] png
        filename = UUID.randomUUID()+"."+fileCompartments[1];
        Path resovledPath = fileLocationStorage.resolve(filename);

        try {
            Files.copy(file.getInputStream(), resovledPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex){
            return ex.getMessage();
        }

        return null;
    }

    @Override
    public String deleteFileByName(String filename) {
        return null;
    }

    @Override
    public String deleteAllFiles() {
        return null;
    }
}
