package com.quizapi.controller;

import com.quizapi.dto.QuestionDto;
import com.quizapi.service.QuestionService;
import com.quizapi.service.impl.QuestionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_QUIZ_MANAGER')")
    @PostMapping("/quiz/{quizId}")
    public ResponseEntity<QuestionDto> createQuestion(QuestionDto questionDto, @PathVariable int quizId) {
        return ResponseEntity.ok(questionService.saveQuestion(questionDto, quizId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_QUIZ_MANAGER')")
    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDto> updateQuestion(QuestionDto questionDto, @PathVariable int questionId) {
        return ResponseEntity.ok(questionService.updateQuestion(questionDto, questionId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_QUIZ_MANAGER')")
    @DeleteMapping("/{questionId}")
    public void deleteQuestion(@PathVariable int questionId) {
        questionService.deleteQuestion(questionId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_QUIZ_MANAGER')")
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByQuizId(@PathVariable int quizId) {
        return ResponseEntity.ok(questionService.getQuestionsByQuizId(quizId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_QUIZ_MANAGER')")
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable int questionId) {
        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }
}
