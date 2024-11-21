package com.quizapi.service;

import com.quizapi.dto.CategoryDto;
import com.quizapi.entity.Category;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {
    CategoryDto categoryToDto(Category category);

    Category dtoToCategory(CategoryDto categoryDto);

    CategoryDto createCategory(CategoryDto categoryDto, MultipartFile file);

    CategoryDto getCategory(int categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto,int categoryId,MultipartFile file);

    void deleteCategory(int categoryId);

}
