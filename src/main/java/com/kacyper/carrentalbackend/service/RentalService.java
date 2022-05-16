package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.domain.Car;
import com.kacyper.carrentalbackend.domain.Rental;
import com.kacyper.carrentalbackend.domain.Status;
import com.kacyper.carrentalbackend.domain.User;
import com.kacyper.carrentalbackend.dto.RentalDto;
import com.kacyper.carrentalbackend.dto.RentalToBeExtendedDto;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.exceptions.RentalNotFoundException;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.repository.CarRepository;
import com.kacyper.carrentalbackend.repository.RentalRepository;
import com.kacyper.carrentalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Transactional
@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public Rental getRentalById(Long id) throws RentalNotFoundException {
        return  rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public List<Rental> getRentalsByUserId(Long id) {
        List<Rental> rentalList = getRentals();
        return rentalList.stream()
                .filter(rental -> rental.getUser().getId().equals(id))
                .collect(Collectors.toList());
    }

    public Rental createRental(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        User user = userRepository.findById(rentalDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(rentalDto.getCarId()).orElseThrow(CarNotFoundException::new);
        car.setStatus(Status.RENTED);

        Rental rental = new Rental(
                rentalDto.getRentedFrom(),
                rentalDto.getRentedTo(),
                user,
                car);
        return rentalRepository.save(rental);
    }

    public Rental modifyRental(RentalDto rentalDto) throws UserNotFoundException, RentalNotFoundException, CarNotFoundException {
        User user = userRepository.findById(rentalDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(rentalDto.getCarId()).orElseThrow(CarNotFoundException::new);
        Rental rental = rentalRepository.findById(rentalDto.getId()).orElseThrow(RentalNotFoundException::new);

        rental.setUser(user);
        rental.setCar(car);
        rental.setRentedFrom(rentalDto.getRentedFrom());
        rental.setRentedTo(rentalDto.getRentedTo());
        updateDuration(rental);
        updateCost(rental);

        return rental;
    }

    public Rental extendRental(RentalToBeExtendedDto rentalToBeExtendedDto) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(rentalToBeExtendedDto.getRentalId()).orElseThrow(RentalNotFoundException::new);

        LocalDate updateReturnDate = rental.getRentedTo().plusDays(rentalToBeExtendedDto.getExtension());
        rental.setRentedTo(updateReturnDate);
        updateDuration(rental);
        updateCost(rental);

        return rental;
    }

    public void endRental(Long id) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);

        rental.getUser().getRentalList().remove(rental);
        rental.getCar().getRentalList().remove(rental);
        rental.getCar().setStatus(Status.AVAILABLE);

        rentalRepository.deleteById(id);
    }

    public void updateDuration(Rental rental) {

        if (rental.getRentedTo().isAfter(rental.getRentedFrom())) {
            rental.setDuration(DAYS.between(rental.getRentedFrom(), rental.getRentedTo()));
        } else {
            rental.setDuration(0L);
        }
    }

    public void updateCost(Rental rental) {

        BigDecimal updateCost = rental.getCar().getDailyCost().multiply(new BigDecimal(rental.getDuration()));
        rental.setCost(updateCost);

    }

}