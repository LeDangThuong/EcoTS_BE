package com.example.EcoTS.Services.AuthService;

import com.example.EcoTS.Models.Verifications;
import com.example.EcoTS.Repositories.VerificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationService {
    @Autowired
    VerificationRepository verificationRepository;
    public boolean checkOTP(Long codeOTP, String email) {
        //verificationRepository.deleteExpiredVerifications();
        Optional<Verifications> verificationOpt = verificationRepository.findByCodeOTP(codeOTP);
        if (verificationOpt.isPresent()) {
            Verifications verification = verificationOpt.get();
            if (verification.getEmail().equals(email) && verification.getExpireTime().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }
}
