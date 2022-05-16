package com.kacyper.carrentalbackend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AdminConfiguration {
    @Value("kksykiewicz@gmail.com")
    private String adminMail;
}