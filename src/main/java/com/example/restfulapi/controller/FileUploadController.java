package com.example.restfulapi.controller;
import com.example.restfulapi.model.response.FileResponse;
import com.example.restfulapi.service.FileUploadService;
import com.example.restfulapi.utils.Response;
import com.github.pagehelper.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/file")
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

    // allowed extension )(jpg, png, ...)
    // size profile image
    private final List<String> ALLOWED_EXTENSIONS = List.of("jpg","png", "jpeg");
    private final long MAX_PROFILE_SIZE = 5 * 1024 * 1024; // 5MB

    @PostMapping("/upload")
    public Response<FileResponse> fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        FileResponse response = uploadFile(file);
        return Response.<FileResponse>ok().setSuccess(true).setMessage("Successfully uploaded a file!").setPayload(response);
    }

    @GetMapping("/download-file/{filename}")
    public ResponseEntity<?>  downloadFile (@PathVariable String filename, HttpServletRequest request) throws Exception {
        // load file as Resource
        Resource resource = fileUploadService.loadFileAsResource(filename);
        // Try to determine file content
        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException exception){
            System.out.println("Couldn't determine the file type ");
        }
        if(contentType==null){
            contentType="application/octet-stream";
        }
        return ResponseEntity.ok().
                contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ resource.getFilename()+"\"")
                .body(resource);
    }


    // helper method
    @PostMapping("/multiple-file-upload")
    public Response<List<FileResponse>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){

            List<FileResponse> filenames = Arrays.stream(files).map(file -> {
                try {
                    return uploadFile(file);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).toList();
            return Response.<List<FileResponse>>ok().setPayload(filenames).setMessage("Successfully uploaded a file!");

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

    private FileResponse uploadFile(MultipartFile file) throws Exception {
        if(file.isEmpty()){
            throw new IllegalArgumentException("File cannot be empty!");
        }
        if(file.getSize()> MAX_PROFILE_SIZE)
            throw new MaxUploadSizeExceededException(MAX_PROFILE_SIZE);

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if(!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())){
            throw new IllegalArgumentException("Allowed extension are : jpeg, png and jpg");
        }

        String filename = fileUploadService.uploadFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download-file/").path(filename).toUriString();
        return new FileResponse().setFilename(filename).setFileDownloadUri(fileDownloadUri).setFileType(file.getContentType()).setSize(file.getSize());
    }

}
