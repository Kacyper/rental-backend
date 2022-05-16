package com.kacyper.carrentalbackend.service.mailService;

import com.kacyper.carrentalbackend.domain.Mail;
import com.kacyper.carrentalbackend.domain.Rental;
import com.kacyper.carrentalbackend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailToUserService {

    private final MailSenderService mailSenderService;
    private final AdminConfiguration adminConfiguration;

    private static final String HELLO = "New account has been created.";
    private static final String RENT_CREATE = "New rental has been created.";
    private static final String RENT_MODIFY = "Your rental has been modified.";
    private static final String RENT_EXTEND = "Your rental has been extended.";
    private static final String RENT_END = "Your rental has been ended.";

    @Autowired
    public MailToUserService(MailSenderService mailSenderService, AdminConfiguration adminConfiguration) {
        this.mailSenderService = mailSenderService;
        this.adminConfiguration = adminConfiguration;
    }

    public void sendMailCreatingUser(User user) {
        mailSenderService.sendMail(new Mail(
                user.getEmail(),
                HELLO,
                helloMessage(user)));
    }

    public void sendMailRegardingRental(Rental rental, String subjectType) {
        mailSenderService.sendMail(new Mail(
                rental.getUser().getEmail(),
                adminConfiguration.getAdminMail(),
                checkMailSubject(subjectType),
                rentMessage(rental, subjectType)));
    }

    private String checkMailSubject(String subjectType) {
        String subject = null;

        switch (subjectType) {
            case "created":
                subject = RENT_CREATE;
                break;
            case "modified":
                subject = RENT_MODIFY;
                break;
            case "extended":
                subject = RENT_EXTEND;
                break;
            case "ended":
                subject = RENT_END;
                break;
        }
        return subject;
    }

    private String rentMessage(Rental rental, String messageType) {
        return ("Hi " +rental.getUser().getFirstName() + ", " + "\n" +
                "\n Your rental has been " + messageType + ". Here are the details: \n" +
                "\n Rental ID: " + rental.getId() + "\n Your rental starts: " + rental.getRentedFrom() +
                "\n and it ends: " + rental.getRentedTo() + "\n Rental will last for: " + rental.getDuration() + "days" +
                "\n That will cost you: " + rental.getCost() + " PLN" + "\n And the car you will be driving is following " +
                rental.getCar().getCarManufacture() + " - " + rental.getCar().getModel() + "\n" +
                "\n Have a great day!" + "\n Thanks for choosing us! Drive safe!");
    }

    private String helloMessage(User user) {
        return ("Hi " + user.getFirstName() + ", " +
                "\n Nice to see you on board! Can you take a moment and check your details: " + "\n" +
                "\n Your ID: " + user.getId() + "\n Your First Name: " + user.getFirstName() +
                "\n Your Last Name: " + user.getLastName() +
                "\n Your Phone Number: " + user.getPhoneNumber() +
                "\n Your Login (Email address): " + user.getEmail() + "\n" +
                "\n Having any questions? Feel free to ask them." +
                "\n Have a great day and see you soon!");
    }

}