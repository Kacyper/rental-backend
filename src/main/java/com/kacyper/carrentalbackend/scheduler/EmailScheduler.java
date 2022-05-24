package com.kacyper.carrentalbackend.scheduler;

import com.kacyper.carrentalbackend.config.AdminConfiguration;
import com.kacyper.carrentalbackend.domain.Mail;
import com.kacyper.carrentalbackend.service.mailService.MailSenderService;
import com.kacyper.carrentalbackend.strategy.MailBodyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private final MailSenderService mailSenderService;
    private final AdminConfiguration adminConfiguration;
    private final MailBodyService mailBodyService;
    private static final String SUBJECT = "Rent Email";

    public EmailScheduler(MailSenderService mailSenderService, AdminConfiguration adminConfiguration, MailBodyService mailBodyService) {
        this.mailSenderService = mailSenderService;
        this.adminConfiguration = adminConfiguration;
        this.mailBodyService = mailBodyService;
    }

//    @Scheduled(fixedDelay = 10000000)
    public void sendInformationMail() {
        mailSenderService.sendMail(Mail.builder()
                .mailTo(adminConfiguration.getAdminMail())
                .subject(SUBJECT)
                .message(mailBodyService.mailBodyCreate())
                .toCC(null)
                .build());
    }
}
