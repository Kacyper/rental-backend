package com.kacyper.carrentalbackend.facade;

import com.kacyper.carrentalbackend.dto.AdminTokenDto;
import com.kacyper.carrentalbackend.mapper.AdminTokenMapper;
import com.kacyper.carrentalbackend.service.AdminTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminTokenFacade {
    private final AdminTokenMapper adminTokenMapper;
    private final AdminTokenService adminTokenService;

    public boolean existsByToken(String token) {
        return adminTokenService.existsByToken(token);
    }

    public AdminTokenDto createToken() {
        return adminTokenMapper.mapToAdminTokenDto(adminTokenService.saveToken());
    }

    public void deleteAllTokens() {
        adminTokenService.deleteAllTokens();
    }
}