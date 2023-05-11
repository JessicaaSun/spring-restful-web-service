package com.example.restfulapi.controller;
import com.example.restfulapi.model.response.FileResponse;
import com.example.restfulapi.service.FileUploadService;
import com.example.restfulapi.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

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
    public Response<?> fileUpload(@RequestParam MultipartFile file){
        String filename = fileUploadService.uploadFile(file);
        return Response.<Object>ok().setSuccess(true).setMessage("Successfully added");
    }
}
