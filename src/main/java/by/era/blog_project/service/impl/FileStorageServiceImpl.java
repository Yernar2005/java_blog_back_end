package by.era.blog_project.service.impl;

import by.era.blog_project.service.FileStorageService;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadPath;

    public FileStorageServiceImpl(@Value("${file.upload-dir}") String  uploadDir) {

        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        try{
            Files.createDirectories(this.uploadPath);
        }catch (Exception e){
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", e);
        }

    }

    @Override
    public String storageFile(MultipartFile file){
        try{
            String ext = "";
            String original = file.getOriginalFilename();
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf('.'));
            }
            String filename = UUID.randomUUID().toString() + ext;
            Path target = uploadPath.resolve(filename);
            try(InputStream in = file.getInputStream()){
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            return "uploads/" + filename;
        }
        catch (Exception e){
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

    }

}
