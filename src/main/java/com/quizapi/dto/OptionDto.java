package com.quizapi.dto;

import com.quizapi.entity.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {

    private int optionId;
    @NotBlank
    private String optionText;
    @NotBlank
    private boolean isCorrect;

}
