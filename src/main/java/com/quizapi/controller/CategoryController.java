package com.quizapi.controller;

import com.quizapi.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto) {
        return null;
    }
}
