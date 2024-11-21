package com.quizapi.controller;

import com.quizapi.dto.QuizDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class QuizController {

    @PostMapping("/user/{userId}/category/{categoryId}/quiz")
    public ResponseEntity<?> createQuiz(Integer userId, Integer categoryId, QuizDto quizDto){
        return ResponseEntity.ok(null);
    }
}
