package com.kacyper.carrentalbackend.api.mailVerifier.client;

import com.kacyper.carrentalbackend.api.mailVerifier.config.MailVerifierConfig;
import com.kacyper.carrentalbackend.dto.api.mail.MailVerifierDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class MailVerifierClient {

    private final RestTemplate restTemplate;

    private final MailVerifierConfig mailVerifierConfig;

    @Autowired
    public MailVerifierClient(RestTemplate restTemplate, MailVerifierConfig mailVerifierConfig) {
        this.restTemplate = restTemplate;
        this.mailVerifierConfig = mailVerifierConfig;
    }

    public MailVerifierDto verifyMail(String email) {
        URI url = getEmailVerificationUri(email);
        return restTemplate.getForObject(url, MailVerifierDto.class);
    }

    private URI getEmailVerificationUri(String email) {
        return UriComponentsBuilder.fromHttpUrl(mailVerifierConfig.getEmailVerificationApiEndpoint())
                .queryParam("apiKey", mailVerifierConfig.getEmailVerificationApiKey())
                .queryParam("emailAddress", email)
                .build().encode().toUri();
    }
}