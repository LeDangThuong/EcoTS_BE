package com.example.EcoTS.Services.SecurityService;

import com.example.EcoTS.DTOs.Request.Auth.SignInDTO;
import com.example.EcoTS.DTOs.Request.Auth.SignUpDTO;
import com.example.EcoTS.Enum.Roles;
import com.example.EcoTS.Models.*;
import com.example.EcoTS.Repositories.*;
import com.example.EcoTS.Services.Location.LocationService;
import com.example.EcoTS.Utils.EmailUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.mail.MessagingException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PointRepository pointRepository;
    private final ResultRepository resultRepository;
    private final UserAchievementRepository userAchievementRepository;
    @Autowired
    private QuizTopicRepository quizTopicRepository;
    @Autowired
    private UserProgressRepository userProgressRepository;
    @Autowired
    private VerificationRepository verificationRepository;
    @Autowired
    private EmailUtils emailUtil;
    @Autowired
    private LocationService locationService;
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            PointRepository pointRepository, ResultRepository resultRepository, UserAchievementRepository userAchievementRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.pointRepository = pointRepository;
        this.resultRepository = resultRepository;
        this.userAchievementRepository = userAchievementRepository;
    }

    @Transactional
    public Users signup(SignUpDTO input, Roles roles, Long locationId) {
        Optional<Users> existingUser = userRepository.findByUsername(input.getUsername());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username '" + input.getUsername() + "' already exists. Please choose a different username.");
        }

        Users user = new Users();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setFullName(input.getFullName());
        user.setDayOfBirth(input.getDayOfBirth());
        user.setRole(roles != null ? roles.name() : Roles.CUSTOMER.name());

        Users savedUser = userRepository.save(user);

        if (Roles.CUSTOMER.equals(roles)) {
            Points points = new Points();
            points.setPoint(0.0);
            points.setUser(savedUser);
            points.setSaveCo2(0L);
            points.setTotalTrashCollect(0L);
            pointRepository.save(points);

            Results results = new Results();
            results.setUser(user);
            results.setNumberOfTimeDonate(0);
            results.setNumberOfTimeDetect(0);
            results.setMaximumPoints(0.0);
            results.setPointDonate(0.0);
            results.setSaveCo2(0.0);
            resultRepository.save(results);

            UserAchievement userAchievement = new UserAchievement();
            userAchievement.setUser(user);
            userAchievement.setBadgeUrl(new ArrayList<>());
            userAchievementRepository.save(userAchievement);

            List<QuizTopic> quizTopics = quizTopicRepository.findAll();
            for (QuizTopic quizTopic : quizTopics) {
                UserProgress userProgress = new UserProgress();
                userProgress.setTopicId(quizTopic.getId());
                userProgress.setUser(user);
                userProgress.setProgress(0.0);
                userProgressRepository.save(userProgress);
            }
        }
        if (locationId != null) {
            locationService.addEmployeeToLocation(savedUser.getId(), locationId);
        }

        return savedUser;
    }

    public Users authenticate(SignInDTO input) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password. Please check your credentials.");
        }

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found. Please check your username."));
    }
    public String forgot_password(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        try {
            emailUtil.sendSetPasswordEmail(user.getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Successful! Please check your email";
    }
    public String reset_password(String email, Long otp, String newPassword, String confirmPassword){
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        Verifications veritifications = verificationRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Ver not found with this email "));

        if(veritifications.getCodeOTP().equals(otp)){
            if(newPassword.equals(confirmPassword)){
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return "Successful! Password was reset";
            }else {
                return "Password do not match! Please fill again";
            }
        }else {
            return "OTP wrong! Please fill again";
        }
    }
}
