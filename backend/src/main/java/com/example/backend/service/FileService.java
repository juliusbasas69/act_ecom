package com.example.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    
    public boolean saveProductImage(MultipartFile image, String fileName);

    public boolean deleteProductImage(String fileName);
}
