package com.tutorial.apidemo.Springboot.tutorial.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;
@Service
public class ImageStorageService implements IStorageService{
    private final Path storageFolder = Paths.get("uploads");
    //contructor
    public ImageStorageService(){
        try{
            Files.createDirectories(storageFolder);
        }
        catch (IOException exception){
            throw new RuntimeException("Cannot initialize storage", exception);
        }
    }
    private boolean isIMageFile(MultipartFile file){
        //let install FilenameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png","jpg","jpeg","bmp"}).contains(fileExtension.trim().toLowerCase());
    }
    @Override
    public String storeFile(MultipartFile file) {
        try{
            System.out.println("haha");
            if(file.isEmpty()){
                throw new RuntimeException("Failed to store empty file");
            } //check file image ?
            if(!isIMageFile(file)){
                throw new RuntimeException("You can only upload file image");
            }
            float fileSizeInMegabytes = file.getSize()/ 1_000_000.0f;
            if(fileSizeInMegabytes > 5.0f){
                throw new RuntimeException("File must be <= 5MB");
            }
            //file must be rename
            String fileExtention = FilenameUtils.getExtension(file.getOriginalFilename());
            String genereatedFileName = UUID.randomUUID().toString().replace("-","");
            genereatedFileName= genereatedFileName+"."+fileExtention;
            Path destinationFilePath = this.storageFolder.resolve(
                    Paths.get(genereatedFileName)).normalize().toAbsolutePath();
            if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw new RuntimeException("Can not store file outside current dictonary");
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return genereatedFileName;
        }

        catch (IOException exception){
            throw new RuntimeException("Failed to store file",exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try{
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else{
                throw new RuntimeException("Could not read file"+ fileName);
            }
        }
        catch (IOException exception){
            throw new RuntimeException("Could not read file"+fileName,exception);
        }
    }

    @Override
    public void deleteAllFiles() {

    }


}
