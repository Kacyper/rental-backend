package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.domain.AdminToken;
import com.kacyper.carrentalbackend.repository.AdminTokenRepository;
import com.kacyper.carrentalbackend.service.mailService.MailSenderService;
import com.kacyper.carrentalbackend.strategy.AdminMail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminTokenService {
    private final AdminTokenRepository adminTokenRepository;

    private final MailSenderService mailSenderService;

    private final AdminMail adminMail;

    public Boolean existsByToken(final String token) {
        return adminTokenRepository.existsByToken(token);
    }

    public AdminToken saveToken() {
        adminTokenRepository.deleteAll();
        String tokenString = UUID.randomUUID().toString();
        AdminToken adminToken = AdminToken.builder().token(tokenString).build();
        mailSenderService.sendMail(adminMail.mailAdminToken(tokenString));
        return adminTokenRepository.save(adminToken);
    }

    public void deleteAllTokens() {
        adminTokenRepository.deleteAll();
    }
}