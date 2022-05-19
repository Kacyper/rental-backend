package com.kacyper.carrentalbackend.mapper;

import com.kacyper.carrentalbackend.domain.AdminToken;
import com.kacyper.carrentalbackend.dto.AdminTokenDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminTokenMapper {
    public AdminToken mapToAdminToken(final AdminTokenDto adminTokenDto) {
        return AdminToken.builder()
                .token(adminTokenDto.getToken())
                .build();
    }

    public AdminTokenDto mapToAdminTokenDto(final AdminToken adminToken) {
        return AdminTokenDto.builder()
                .id(adminToken.getId())
                .token(adminToken.getToken())
                .build();
    }
}