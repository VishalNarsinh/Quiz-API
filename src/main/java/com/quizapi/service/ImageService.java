package com.quizapi.service;

import com.cloudinary.Cloudinary;
import com.quizapi.utils.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    ImageUploadResponse uploadImage(MultipartFile file, String folder) throws IOException;

    String getExtension(String fileName);

    public void deleteImage(String publicId) throws IOException;
}
