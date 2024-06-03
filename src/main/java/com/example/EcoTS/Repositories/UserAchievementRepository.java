package com.example.EcoTS.Repositories;

import com.example.EcoTS.Models.UserAchievement;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
}
