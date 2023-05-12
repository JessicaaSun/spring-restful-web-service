package com.example.restfulapi.service.serviceImpl;

import com.example.restfulapi.service.FileUploadService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final String serverLocation = "src/main/resources/images/";
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
        //format filename;
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
            return filename;
        } catch (Exception ex){
            return ex.getMessage();
        }
    }

    @Override
    public String deleteFileByName(String filename) {
        Path imagesLocation = Paths.get(serverLocation);
        List<File> allFiles = List.of(imagesLocation.toFile().listFiles());
        //filter file that we're going to delete
        File deletedFile = allFiles.stream().filter(
                file -> file.getName().equals(filename)
        ).findFirst().orElse(null);

        try {
            if(deletedFile != null){
                Files.delete(deletedFile.toPath());
                return "delete success";
            } else {
               return "could not delete";
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return "Exception occurred! Fail to delete file";
        }

    }

    @Override
    public String deleteAllFiles() {
        Path imagesLocation = Paths.get(serverLocation);
        File[] files = imagesLocation.toFile().listFiles();

        try{
            System.out.println(files);
            if(files== null || files.length == 0){
                return "There is no file to delete!";
            }
            for(File file: files){
                Files.delete(file.toPath());
            }
            return "delete successfully";
        } catch (Exception ex){
            ex.printStackTrace();
            return "Exception occurred, fail to delete files!";
        }

    }

    @Override
    public Resource loadFileAsResource(String filename) throws Exception{
        Path resourcePath = this.fileLocationStorage.resolve(filename).normalize();
        try{
            Resource resource = new UrlResource(resourcePath.toUri());
            if(resource.exists()){
                return resource;
            } else {
                throw new Exception("Resource doesn't exist!");
            }
        } catch (Exception ex) {
            throw new Exception("Exception occurred!! Fail to download the image");
        }
    }
}
