package com.kacyper.carrentalbackend.service.mailService;

import com.kacyper.carrentalbackend.api.mailVerifier.client.MailVerifierClient;
import com.kacyper.carrentalbackend.dto.api.mail.MailVerifierDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailVerificationService {

    private final MailVerifierClient mailVerifierClient;
    @Autowired
    public MailVerificationService(MailVerifierClient mailVerifierClient) {
        this.mailVerifierClient = mailVerifierClient;
    }
    public MailVerifierDto verifyMail(final String mail) {
        return mailVerifierClient.verifyMail(mail);
    }

}