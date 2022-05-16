package com.kacyper.carrentalbackend.facade;

import com.kacyper.carrentalbackend.domain.*;
import com.kacyper.carrentalbackend.dto.RentalAllDto;
import com.kacyper.carrentalbackend.dto.RentalDto;
import com.kacyper.carrentalbackend.dto.RentalToBeExtendedDto;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.exceptions.RentalNotFoundException;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.mapper.RentalMapper;
import com.kacyper.carrentalbackend.repository.RentalRepository;
import com.kacyper.carrentalbackend.service.RentalService;
import com.kacyper.carrentalbackend.service.mailService.MailToUserService;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RentalFacadeTestSuite {

    @InjectMocks
    private RentalFacade rentalFacade;

    @Mock
    private RentalMapper rentalMapper;

    @Mock
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private MailToUserService mailToUserService;

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

    private RentalAllDto newRentalAllDto() {
        return RentalAllDto.builder()
                .id(1L)
                .rentedFrom(LocalDate.of(2022, 5, 5))
                .rentedTo(LocalDate.of(2022, 5, 10))
                .cost(new BigDecimal(23))
                .carId(1L)
                .carManufacture("Volvo")
                .carModel("XC 90")
                .userFirstName("John")
                .userLastName("Doe")
                .userEmail("mail")
                .userPhoneNumber(123)
                .build();
    }

    @DisplayName("Get Rent By Id")
    @Test
    public void getRentalByIdTest() throws RentalNotFoundException {
        //Given
        Rental rental = newRental();
        RentalAllDto rentalAllDto = newRentalAllDto();

        when(rentalService.getRentalById(1L)).thenReturn(rental);
        when(rentalMapper.mapToRentalAllDto(rental)).thenReturn(rentalAllDto);


        //When
        RentalAllDto rentalId = rentalFacade.getRentalById(1L);

        //Then
        assertEquals(rentalAllDto.getId(), rentalId.getId());
    }

    @DisplayName("Get All Rentals")
    @Test
    public void getAllRentalsTest() {
        //Given
        Rental rental = newRental();
        RentalAllDto rentalAllDto = newRentalAllDto();
        List<Rental> rentalList = Collections.singletonList(rental);
        List<RentalAllDto> rentalAllDtoList = Collections.singletonList(rentalAllDto);

        when(rentalService.getRentals()).thenReturn(rentalList);
        when(rentalMapper.mapToRentalAllDtoList(rentalList)).thenReturn(rentalAllDtoList);

        //When
        List<RentalAllDto> rentalAllDtos = rentalFacade.getAllRentals();

        //Then
        assertNotNull(rentalAllDtoList);
        assertEquals(1, rentalAllDtos.size());
    }

    @DisplayName("Get Rentals By User's Id")
    @Test
    public void getRentalsByUsersIdTest() {
        //Given
        Rental rental = newRental();
        rental.setId(1L);
        RentalAllDto rentalAllDto = newRentalAllDto();
        List<Rental> rentalList = Collections.singletonList(rental);
        List<RentalAllDto> rentalAllDtoList = Collections.singletonList(rentalAllDto);

        when(rentalService.getRentalsByUserId(1L)).thenReturn(rentalList);
        when(rentalMapper.mapToRentalAllDtoList(rentalList)).thenReturn(rentalAllDtoList);

        //When
        List<RentalAllDto> rentList = rentalFacade.getRentalsByUsersId(1L);

        //Then
        assertNotNull(rentList);
        assertEquals(1, rentList.size());
    }

    @DisplayName("Create Rent")
    @Test
    public void createRentTest() throws UserNotFoundException, CarNotFoundException {
        //Given
        Rental rental = newRental();
        RentalDto rentalDto = newRentalDto();
        RentalAllDto rentalAllDto = newRentalAllDto();

        when(rentalService.createRental(rentalDto)).thenReturn(rental);
        when(rentalMapper.mapToRentalAllDto(rental)).thenReturn(rentalAllDto);
        doNothing().when(mailToUserService).sendMailRegardingRental(rental, "created");

        //When
        RentalAllDto createRent = rentalFacade.createRent(rentalDto);

        //Then
        assertEquals(rentalAllDto.getId(), createRent.getId());
        verify(mailToUserService, times(1)).sendMailRegardingRental(rental, "created");
    }

    @DisplayName("Modify Rent")
    @Test
    public void modifyRentTest() throws UserNotFoundException, CarNotFoundException, RentalNotFoundException {
        //Given
        Rental rental = newRental();
        RentalDto rentalDto = newRentalDto();
        RentalAllDto rentalAllDto = newRentalAllDto();

        when(rentalService.modifyRental(rentalDto)).thenReturn(rental);
        when(rentalMapper.mapToRentalAllDto(rental)).thenReturn(rentalAllDto);
        doNothing().when(mailToUserService).sendMailRegardingRental(rental, "modified");

        //When
        RentalAllDto createRent = rentalFacade.modifyRent(rentalDto);

        //Then
        assertEquals(rentalAllDto.getId(), createRent.getId());
        verify(mailToUserService, times(1)).sendMailRegardingRental(rental, "modified");
    }

    @DisplayName("Extend Rent")
    @Test
    public void extendRentTest() throws RentalNotFoundException {
        //Given
        Rental rental = newRental();
        RentalToBeExtendedDto rentalToBeExtendedDto = newRentalToBeExtendedDto();

        RentalAllDto extendedDto = new RentalAllDto(
                1L,
                LocalDate.of(2022, 5, 5),
                LocalDate.of(2022, 5, 15),
                new BigDecimal(232),
                1L,
                "Volvo",
                "XC 90",
                "John",
                "Doe",
                "mail",
                123);

        when(rentalService.extendRental(rentalToBeExtendedDto)).thenReturn(rental);
        when(rentalMapper.mapToRentalAllDto(rental)).thenReturn(extendedDto);
        doNothing().when(mailToUserService).sendMailRegardingRental(rental, "extended");

        //When
        RentalAllDto extendRent = rentalFacade.extendRent(rentalToBeExtendedDto);

        //Then
        assertEquals(extendedDto.getId(), extendRent.getId());
        verify(mailToUserService, times(1)).sendMailRegardingRental(rental, "extended");
    }

    @DisplayName("End Rent")
    @Test
    public void endRentTest() throws RentalNotFoundException {
        //Given
        Rental rental = newRental();

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        doNothing().when(mailToUserService).sendMailRegardingRental(rental, "ended");

        //When
        rentalFacade.endRent(1L);

        //Then
        assertEquals(LocalDate.now(), rental.getRentedTo());
        verify(rentalService, times(1)).endRental(1L);
        verify(mailToUserService, times(1)).sendMailRegardingRental(rental, "ended");
    }
}