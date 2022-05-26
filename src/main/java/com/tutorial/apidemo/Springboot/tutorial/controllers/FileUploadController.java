package com.tutorial.apidemo.Springboot.tutorial.controllers;

import com.tutorial.apidemo.Springboot.tutorial.models.ResponseObject;
import com.tutorial.apidemo.Springboot.tutorial.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {
    //this controller receive image/file from client
    //Inject storage service here
    @Autowired
    private IStorageService storageService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            //save file to folders => use service
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","upload file successfully",generatedFileName)
            );
        }
        catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", exception.getMessage(), "")
            );
        }
    }
    @GetMapping("/files/{fileName:.+}")
    // /files/1677bf62a51d48e486ecccfc37274cd1.jpeg
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try{
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }
        catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }
}
