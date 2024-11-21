package com.quizapi.repository;

import com.quizapi.entity.Question;
import com.quizapi.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByQuiz(Quiz quiz);
}
