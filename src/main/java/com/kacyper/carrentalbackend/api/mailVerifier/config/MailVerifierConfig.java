package com.kacyper.carrentalbackend.api.mailVerifier.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class MailVerifierConfig {

    @Value("${emailverification.api.endpoint}")
    private String emailVerificationApiEndpoint;

    @Value("${emailverification.api.key}")
    private String emailVerificationApiKey;

}