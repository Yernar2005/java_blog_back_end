package by.era.blog_project.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storageFile(MultipartFile file);

}
