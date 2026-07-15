package com.example.backend.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${product.images.path}")
    private String imagesPath;

    @GetMapping("/products/images/{imageName}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable String imageName) {
    
        try {
            System.out.println("Image Name: " + imageName);

Path filePath = Paths.get(imagesPath, imageName);

System.out.println("Path: " + filePath.toAbsolutePath());
System.out.println("Exists: " + Files.exists(filePath));

File directory = new File(imagesPath);

for (File file : directory.listFiles()) {
    System.out.println("File Name Exist: " + file.getName());
}
       
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            System.out.println("IN HERE");

            String contentType = Files.probeContentType(filePath);

            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            byte[] image = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(image);

        } catch (IOException e) {
            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
