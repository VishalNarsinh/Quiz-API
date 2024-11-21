package com.quizapi.service.impl;

import com.quizapi.dto.CategoryDto;
import com.quizapi.entity.Category;
import com.quizapi.exception.ResourceNotFound;
import com.quizapi.repository.CategoryRepository;
import com.quizapi.service.CategoryService;
import com.quizapi.service.ImageService;
import com.quizapi.utils.AppConstants;
import com.quizapi.utils.ImageUploadResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository, ImageService imageService) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
    }

    @Override
    public CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto, MultipartFile file) {
        Category category = dtoToCategory(categoryDto);
        category.setUpdatedAt(new java.util.Date());
        category.setCreatedAt(new java.util.Date());
        if(file!=null && !file.isEmpty()) {
            try {
                ImageUploadResponse imageUploadResponse = imageService.uploadImage(file, AppConstants.CATEGORY_FOLDER);
                category.setCategoryImageUrl(imageUploadResponse.getUrl());
                category.setCategoryImagePublicId(imageUploadResponse.getPublicId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            category.setCategoryImageUrl(AppConstants.DEFAULT_CATEGORY_IMAGE_URL);
            category.setCategoryImagePublicId(AppConstants.DEFAULT_CATEGORY_IMAGE_PUBLIC_ID);
        }
        return categoryToDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto getCategory(int categoryId) {
        return categoryToDto(categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId)));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId,MultipartFile file) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId));
        category.setUpdatedAt(new java.util.Date());
        if(file!=null && !file.isEmpty()) {
            try {
                imageService.deleteImage(category.getCategoryImagePublicId());
                ImageUploadResponse imageUploadResponse = imageService.uploadImage(file, AppConstants.CATEGORY_FOLDER);
                category.setCategoryImageUrl(imageUploadResponse.getUrl());
                category.setCategoryImagePublicId(imageUploadResponse.getPublicId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        categoryRepository.save(category);
        return null;
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId));
        try {
            imageService.deleteImage(category.getCategoryImagePublicId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categoryRepository.delete(category);
    }
}
