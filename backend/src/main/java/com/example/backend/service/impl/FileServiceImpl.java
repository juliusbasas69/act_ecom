package com.example.backend.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.service.FileService;

@Service
public class FileServiceImpl implements FileService{

    @Value("${product.images.path}")
    private String imagesPath;

    @Override
    public boolean saveProductImage(MultipartFile image, String fileName) {

        if (image == null || image.isEmpty()) {
            return false;
        }

        try {
            File directory = new File(imagesPath);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, fileName);

            image.transferTo(file);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteProductImage(String fileName) {

        if (fileName == null || fileName.isBlank()) {
            return false;
        }

        File file = new File(imagesPath, fileName);

        if (!file.exists()) {
            return false;
        }

        return file.delete();
    }
}
