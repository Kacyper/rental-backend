package com.kacyper.carrentalbackend.service.mailService;

import com.kacyper.carrentalbackend.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendMail(final Mail mail) {
        log.info("Email is being prepared...");
        try {
            javaMailSender.send(createMessage(mail));
            log.info("Email has been sent...");
        } catch (MailException e) {
            log.error("Email hasn't been sent. Something went wrong: " + e.getMessage(), e);
        }
    }
    private SimpleMailMessage createMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
