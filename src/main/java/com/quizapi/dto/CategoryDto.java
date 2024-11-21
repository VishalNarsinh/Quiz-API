package com.quizapi.dto;

import com.quizapi.entity.Quiz;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private int categoryId;
    @NotBlank
    private String categoryName;
    private String categoryDescription;

    private String categoryImageUrl;
    private String categoryImagePublicId;

    private Date createdAt;
    private Date updatedAt;

}
