package com.quizapi.service.impl;

import com.quizapi.dto.QuestionDto;
import com.quizapi.entity.Question;
import com.quizapi.entity.Quiz;
import com.quizapi.exception.ResourceNotFound;
import com.quizapi.repository.QuestionRepository;
import com.quizapi.repository.QuizRepository;
import com.quizapi.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    private final QuizRepository quizRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, ModelMapper modelMapper, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
        this.quizRepository = quizRepository;
    }

    @Override
    public QuestionDto questionToDto(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }

    @Override
    public Question dtoToQuestion(QuestionDto questionDto) {
        return modelMapper.map(questionDto, Question.class);
    }

    @Override
    public QuestionDto saveQuestion(QuestionDto questionDto, int quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFound("Quiz", "id", quizId));
        Question question = this.dtoToQuestion(questionDto);
        question.setQuiz(quiz);
        Question save = questionRepository.save(question);
        return this.questionToDto(save);
    }

    @Override
    public QuestionDto updateQuestion(QuestionDto questionDto, int questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFound("Question", "id", questionId));
        question.setQuestionText(questionDto.getQuestionText());
        Question save = questionRepository.save(question);
        return this.questionToDto(save);
    }

    @Override
    public void deleteQuestion(int questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFound("Question", "id", questionId));
        questionRepository.delete(question);
    }

    @Override
    public QuestionDto getQuestionById(int questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFound("Question", "id", questionId));
        return this.questionToDto(question);
    }

    @Override
    public List<QuestionDto> getQuestionsByQuizId(int quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFound("Quiz", "id", quizId));
        List<Question> questionList = questionRepository.findByQuiz(quiz);
//        return questionList.stream().map(this::questionToDto).toList();
//        above toList() returns a immuatable list
        return questionList.stream().map(this::questionToDto).collect(Collectors.toList());
//        this returns a mutable list
    }
}
