package com.quizapi.dto;

import com.quizapi.entity.Option;
import com.quizapi.entity.Quiz;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private int questionId;

    @NotBlank
    private String questionText;


    private List<OptionDto> optionList= new ArrayList<>();

}
