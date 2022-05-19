package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.dto.AdminTokenDto;
import com.kacyper.carrentalbackend.facade.AdminTokenFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/token")
@RequiredArgsConstructor
public class AdminTokenController {

    private final AdminTokenFacade adminTokenFacade;

    @GetMapping(value = "/{token}")
    public ResponseEntity<Boolean> existsToken(@PathVariable String token) {
        return ResponseEntity.ok(adminTokenFacade.existsByToken(token));
    }

    @PostMapping
    public ResponseEntity<AdminTokenDto> createToken() {
        return ResponseEntity.ok(adminTokenFacade.createToken());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTokens() {
        adminTokenFacade.deleteAllTokens();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}