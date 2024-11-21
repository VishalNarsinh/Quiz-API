package com.quizapi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.quizapi.service.ImageService;
import com.quizapi.utils.AppConstants;
import com.quizapi.utils.ImageUploadResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public ImageUploadResponse uploadImage(MultipartFile file, String folder) throws IOException {
        String folderPath = "quiz-api/" + folder;
        String extension = getExtension(file.getOriginalFilename());
        String randomFileName = RandomStringUtils.randomAlphanumeric(10) + extension;
        Map upload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", folderPath, "public_id", randomFileName));
        return ImageUploadResponse.builder()
                .url(upload.get("secure_url").toString())
                .publicId(upload.get("public_id").toString()).build();

    }

    @Override
    public String getExtension(String fileName) {
        if(fileName!=null && fileName.lastIndexOf(".")>0){
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

    @Override
    public void deleteImage(String publicId) throws IOException {
        if(publicId!=null && !publicId.isEmpty() && !publicId.equals(AppConstants.DEFAULT_USER_IMAGE_PUBLIC_ID)) {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }
    }
}
