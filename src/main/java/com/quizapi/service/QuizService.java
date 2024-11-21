package com.quizapi.service;

import com.quizapi.dto.QuizDto;
import com.quizapi.entity.Quiz;
import org.springframework.web.multipart.MultipartFile;

public interface QuizService {
    Quiz dtoToQuiz(QuizDto quizDto);

    QuizDto quizToDto(Quiz quiz);

    QuizDto saveQuiz(Integer userId, Integer categoryId, QuizDto quizDto);
}
