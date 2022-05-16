package com.kacyper.carrentalbackend.strategy;

import com.kacyper.carrentalbackend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumberOfRents implements MailBodyService {

    private final RentalRepository rentalRepository;

    @Autowired
    public NumberOfRents(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public String mailBodyCreate() {
        long rentCount = rentalRepository.count();
        return ("\n Hi Admin," + "\n In this mail you can find statistics regarding number of rentals in your company: " +
                "\n Number of all rentals as of today is: " + rentCount + "\n Good job or not. Depends on the number you see." +
                "\n Have a great day!");

    }
}