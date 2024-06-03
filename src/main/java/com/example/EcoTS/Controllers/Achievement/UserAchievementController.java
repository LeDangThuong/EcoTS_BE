package com.example.EcoTS.Controllers.Achievement;

import com.example.EcoTS.Models.UserAchievement;
import com.example.EcoTS.Models.Users;
import com.example.EcoTS.Repositories.UserAchievementRepository;
import com.example.EcoTS.Repositories.UserRepository;
import com.example.EcoTS.Services.Achievement.ResultService;
import com.example.EcoTS.Services.Achievement.UserAchievementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user-achievement")
@Tag(name = "User Achievement")
public class UserAchievementController {
    @Autowired
    private ResultService resultService;
    @Autowired
    private UserAchievementService userAchievementService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAchievementRepository userAchievementRepository;

    @GetMapping("/get-all-achievement-by-user-id")
    public ResponseEntity<UserAchievement> getAllAchievementByUserId(@RequestParam Long userId)
    {
        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserAchievement userAchievement = userAchievementRepository.findByUsers(users).orElseThrow(() -> new RuntimeException("User achievement not found"));

        return ResponseEntity.ok().body(userAchievement);
    }
}
