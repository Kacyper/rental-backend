package com.kacyper.carrentalbackend.config;

import com.kacyper.carrentalbackend.scheduler.EmailScheduler;
import com.kacyper.carrentalbackend.service.mailService.MailSenderService;
import com.kacyper.carrentalbackend.strategy.MailBodyService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfiguration {

    @Bean
    public EmailScheduler numberOfRentsSchedule(MailSenderService mailSenderService, AdminConfiguration adminConfiguration, @Qualifier("numberOfRents") MailBodyService mailBodyService) {
        return new EmailScheduler(mailSenderService, adminConfiguration, mailBodyService);
    }
}
