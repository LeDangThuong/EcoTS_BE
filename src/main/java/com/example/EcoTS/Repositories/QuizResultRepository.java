package com.example.EcoTS.Repositories;

import com.example.EcoTS.Models.QuizResult;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByUserIdAndQuizId(Long userId, Long quizId);
}
