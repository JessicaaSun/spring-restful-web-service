package com.example.restfulapi.controller;
import com.example.restfulapi.model.response.FileResponse;
import com.example.restfulapi.service.FileUploadService;
import com.example.restfulapi.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {
    @Autowired
    private final FileUploadService fileUploadService;
//    @Value("images/")
//    String path;
//    @PostMapping("/upload")
//    public ResponseEntity<FileResponse> fileResponseResponseEntity(@RequestParam("images") MultipartFile file){
//        String filename = null;
//        try{
//            filename = "http://localhost:8080/images/"+this.fileUploadService.uploadFile(path,file);
//        }catch(IOException e){
//            return new ResponseEntity<>(new FileResponse(null,e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(new FileResponse(filename,"image uploaded successfully!!!"),HttpStatus.OK);
//    }
    @PostMapping("/upload")
    public Response<?> fileUpload(@RequestParam("file") MultipartFile file){
        String filename = fileUploadService.uploadFile(file);
        return Response.<Object>ok().setSuccess(true).setMessage("Successfully added");
    }

    // helper method
    @PostMapping("/multiple-file-upload")
    public Response<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
       List<String> filenames = Arrays.stream(files).map(file -> fileUploadService.uploadFile(file)).collect(Collectors.toList());
       return Response.<Object>ok().setPayload(filenames);
    }

    @DeleteMapping("/delete-file/{filename}")
    public String deleteSingleFile(@PathVariable String filename){
        String result = fileUploadService.deleteFileByName(filename);
        return result;
    }

    @DeleteMapping("/delete-all-files")
    public String deleteAllFiles(){
        return fileUploadService.deleteAllFiles();
    }

    private FileResponse uploadFile(MultipartFile file) {
        String filename = fileUploadService.uploadFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download-file").path(filename).toUriString();
        return new FileResponse().setFilename(filename).setFileDownloadUri(fileDownloadUri).setFileType(file.getContentType()).setSize(file.getSize());
    }
}
