package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.domain.*;
import com.kacyper.carrentalbackend.dto.RentalDto;
import com.kacyper.carrentalbackend.dto.RentalToBeExtendedDto;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.exceptions.RentalNotFoundException;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.repository.CarRepository;
import com.kacyper.carrentalbackend.repository.RentalRepository;
import com.kacyper.carrentalbackend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RentalServiceTestSuite {

    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    private Car newCar() {
        return Car.builder()
                .id(1L)
                .carManufacture("Volvo")
                .dailyCost(new BigDecimal(24))
                .fuel(Fuel.DIESEL)
                .mileage(233)
                .model("XC 90")
                .productionYear(2020)
                .vehicleClass(VehicleClass.PREMIUM)
                .vin("123123")
                .build();
    }

    private User newUser() {
        return User.builder()
                .id(1L)
                .email("user@mail.com")
                .password("pass")
                .firstName("Joe")
                .lastName("Doe")
                .phoneNumber(123)
                .accountCreationDate(LocalDate.now())
                .build();
    }

    private Rental newRental() {
        return Rental.builder()
                .id(1L)
                .rentedFrom(LocalDate.of(2022, 5, 5))
                .rentedTo(LocalDate.of(2022, 5, 10))
                .user(newUser())
                .car(newCar())
                .build();
    }

    private RentalDto newRentalDto() {
        return RentalDto.builder()
                .id(1L)
                .rentedFrom(LocalDate.of(2022, 5, 5))
                .rentedTo(LocalDate.of(2022, 5, 10))
                .userId(1L)
                .carId(1L)
                .build();
    }

    private RentalToBeExtendedDto newRentalToBeExtendedDto() {
        return RentalToBeExtendedDto.builder()
                .rentalId(newRental().getId())
                .extension(10)
                .build();
    }

    @DisplayName("Get Rental By Id")
    @Test
    public void getRentalByIdTest() throws RentalNotFoundException {
        //Given
        Rental rental = newRental();

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        //When
        Rental rentId = rentalService.getRentalById(1L);

        //Then
        Assertions.assertEquals(rentId.getId(), rentId.getId());


    }

    @DisplayName("Get All Rentals")
    @Test
    public void getRentalsTest() throws RentalNotFoundException {
        //Given
        Rental rental = newRental();

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        //When
        Rental rental1 = rentalService.getRentalById(1L);

        //Then
        Assertions.assertEquals(rental1.getId(), rental.getId());
    }

    @DisplayName("Get Rentals By User Id")
    @Test
    public void getRentalsByUserIdTest() {
        //Given
        Rental rental = newRental();
        rental.setId(1L);
        List<Rental> rentalList = Collections.singletonList(rental);

        when(rentalService.getRentals()).thenReturn(rentalList);

        //When
        List<Rental> resultList = rentalService.getRentalsByUserId(1L);

        //Then
        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(1, resultList.size());
        Assertions.assertEquals(1L, (long) resultList.get(0).getId());
    }

    @DisplayName("Create Rental")
    @Test
    public void createRentalTest() throws CarNotFoundException, UserNotFoundException {
        //Given
        User user = newUser();
        Car car = newCar();
        Rental rental = newRental();
        RentalDto rentalDto = newRentalDto();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        when(rentalRepository.save(any())).thenReturn(rental);

        //When
        Rental newRent = rentalService.createRental(rentalDto);

        //Then
        Assertions.assertEquals(newRent.getCar().getCarManufacture(), car.getCarManufacture());
        Assertions.assertEquals(newRent.getUser().getFirstName(), user.getFirstName());
        Assertions.assertEquals(newRent.getRentedFrom(), LocalDate.of(2022, 5, 5));
        Assertions.assertEquals(newRent.getRentedTo(), LocalDate.of(2022, 5, 10));
    }

    @DisplayName("Modify Rental")
    @Test
    public void modifyRentalTest() throws UserNotFoundException, RentalNotFoundException, CarNotFoundException {
        //Given
        User user = newUser();
        Car car = newCar();
        Rental rental = newRental();
        RentalDto rentalDto = newRentalDto();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        //When
        Rental changedRent = rentalService.modifyRental(rentalDto);

        //Then
        Assertions.assertEquals(rentalDto.getRentedFrom(), changedRent.getRentedFrom());
        Assertions.assertEquals(rentalDto.getRentedTo(), changedRent.getRentedTo());
    }

    @DisplayName("Extend Rental")
    @Test
    public void extendRentalTest() throws RentalNotFoundException {
        //Given
        Rental rental = newRental();
        RentalToBeExtendedDto rentalToBeExtendedDto = newRentalToBeExtendedDto();

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        //When
        Rental rent = rentalService.extendRental(rentalToBeExtendedDto);

        //Then
        Assertions.assertEquals(rent.getRentedFrom(), LocalDate.of(2022, 5, 5));
        Assertions.assertEquals(rent.getRentedTo(), LocalDate.of(2022, 5, 20));
    }

    @DisplayName("Update Duration")
    @Test
    public void updateDurationTest() {
        //Given
        Rental rental = newRental();
        rental.setRentedTo(LocalDate.of(2022, 5, 25));

        //When
        rentalService.updateDuration(rental);

        //Then
        Assertions.assertEquals(20L, (long) rental.getDuration());
    }

    @DisplayName("Update Cost")
    @Test
    public void updateCostTest() {
        //Given
        Rental rental = newRental();
        rental.setDuration(5L);

        //When
        rentalService.updateCost(rental);

        //Then
        Assertions.assertEquals(new BigDecimal(120), rental.getCost());
    }
}
