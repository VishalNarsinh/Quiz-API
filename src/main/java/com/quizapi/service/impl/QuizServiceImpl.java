package com.quizapi.service.impl;

import com.quizapi.dto.QuizDto;
import com.quizapi.entity.Category;
import com.quizapi.entity.Quiz;
import com.quizapi.entity.User;
import com.quizapi.exception.ResourceNotFound;
import com.quizapi.repository.CategoryRepository;
import com.quizapi.repository.QuizRepository;
import com.quizapi.repository.UserRepository;
import com.quizapi.service.QuizService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuizServiceImpl implements QuizService {

    private final ModelMapper modelMapper;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public QuizServiceImpl(ModelMapper modelMapper, QuizRepository quizRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Quiz dtoToQuiz(QuizDto quizDto) {
        return modelMapper.map(quizDto, Quiz.class);
    }

    @Override
    public QuizDto quizToDto(Quiz quiz) {
        return modelMapper.map(quiz, QuizDto.class);
    }

    @Override
    public QuizDto saveQuiz(Integer userId, Integer categoryId, QuizDto quizDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId));
        Quiz quiz = dtoToQuiz(quizDto);
        quiz.setCategory(category);
        quiz.setUser(user);
        quiz.setCreatedAt(new Date());
        quiz.setUpdatedAt(new Date());
        Quiz save = quizRepository.save(quiz);
        return quizToDto(save);
    }

    public void deleteQuiz(int quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFound("Quiz", "id", quizId));
        quizRepository.delete(quiz);
    }

    public QuizDto updateQuiz(int quizId, QuizDto quizDto) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFound("Quiz", "id", quizId));
        quiz.setQuizTitle(quizDto.getQuizTitle());
        quiz.setQuizDescription(quizDto.getQuizDescription());
        quiz.setUpdatedAt(new Date());
        Quiz save = quizRepository.save(quiz);
        return quizToDto(save);
    }
}
