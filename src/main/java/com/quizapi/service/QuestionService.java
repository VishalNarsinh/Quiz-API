package com.quizapi.service;

import com.quizapi.dto.QuestionDto;
import com.quizapi.entity.Question;

import java.util.List;

public interface QuestionService {
    QuestionDto questionToDto(Question question);

    Question dtoToQuestion(QuestionDto questionDto);

    QuestionDto saveQuestion(QuestionDto questionDto, int quizId);

    QuestionDto updateQuestion(QuestionDto questionDto, int questionId);

    void deleteQuestion(int questionId);

    QuestionDto getQuestionById(int questionId);

    List<QuestionDto> getQuestionsByQuizId(int quizId);
}
