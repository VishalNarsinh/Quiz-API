package com.quizapi.dto;

import com.quizapi.entity.Category;
import com.quizapi.entity.Question;
import com.quizapi.entity.User;
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
public class QuizDto {

    private int quizId;
    @NotBlank
    private String quizTitle;
    private String quizDescription;


    private Date createdAt;
    private Date updatedAt;

    private CategoryDto category;

    private UserDto user;

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.REMOVE)
    private List<QuestionDto> questionList = new ArrayList<>();
}
