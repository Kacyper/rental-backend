package com.kacyper.carrentalbackend.strategy;

import com.kacyper.carrentalbackend.config.AdminConfiguration;
import com.kacyper.carrentalbackend.domain.Mail;
import com.kacyper.carrentalbackend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMail implements MailBodyService {

    private final RentalRepository rentalRepository;

    private final AdminConfiguration adminConfiguration;

    @Autowired
    public AdminMail(RentalRepository rentalRepository, AdminConfiguration adminConfiguration) {
        this.rentalRepository = rentalRepository;
        this.adminConfiguration = adminConfiguration;
    }

    @Override
    public String mailBodyCreate() {
        long rentCount = rentalRepository.count();
        return ("\n Hi Admin," + "\n In this mail you can find statistics regarding number of rentals in your company: " +
                "\n Number of all rentals as of today is: " + rentCount + "\n Good job or not. Depends on the number you see." +
                "\n Have a great day!");

    }

    public Mail mailAdminToken(String token) {
        String adminEmail = adminConfiguration.getAdminMail();
        String subject = "New Admin Token Has Been Generated For You.";
        String message = "Enter your code to log in as an admin: \n" + token;
        return new Mail(adminEmail, subject, message);

    }
}