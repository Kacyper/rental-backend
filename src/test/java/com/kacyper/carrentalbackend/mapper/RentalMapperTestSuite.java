package com.kacyper.carrentalbackend.mapper;

import com.kacyper.carrentalbackend.domain.*;
import com.kacyper.carrentalbackend.dto.RentalAllDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RentalMapperTestSuite {

    @Autowired
    private RentalMapper rentalMapper;

    private Car sampleCar() {
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

    private User sampleUser() {
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

    private Rental sampleRent() {
        return Rental.builder()
                .id(1L)
                .rentedFrom(LocalDate.of(2022, 5, 5))
                .rentedTo(LocalDate.of(2022, 5, 10))
                .user(sampleUser())
                .car(sampleCar())
                .build();
    }

    @DisplayName("Map To Rent All Dto")
    @Test
    public void mapToRentalAllDtoTest() {
        //Given
        Rental rental = sampleRent();
        rental.setId(1L);

        //When
        RentalAllDto rentalAllDto = rentalMapper.mapToRentalAllDto(rental);

        //Then
        assertEquals(1L, (long) rentalAllDto.getId());
        assertEquals(LocalDate.of(2022, 5, 5), rentalAllDto.getRentedFrom());
        assertEquals(LocalDate.of(2022, 5, 10), rentalAllDto.getRentedTo());
        assertEquals("Volvo", rentalAllDto.getCarManufacture());
        assertEquals("XC 90", rentalAllDto.getCarModel());
        assertEquals("Joe", rentalAllDto.getUserFirstName());
        assertEquals("Doe", rentalAllDto.getUserLastName());
        assertEquals(123, rentalAllDto.getUserPhoneNumber());
    }

    @DisplayName("Map To Rent All Dto List")
    @Test
    public void mapToRentalAllDtoListTest() {
        //Given
        Rental rental = sampleRent();
        rental.setId(1L);
        List<Rental> rentalList = Collections.singletonList(rental);

        //When
        List<RentalAllDto> rentalAllDtoList = rentalMapper.mapToRentalAllDtoList(rentalList);

        //Then
        assertNotNull(rentalAllDtoList);
        assertEquals(1, rentalAllDtoList.size());

        assertEquals(1L, (long) rentalAllDtoList.get(0).getId());
        assertEquals(LocalDate.of(2022, 5, 5), rentalAllDtoList.get(0).getRentedFrom());
        assertEquals(LocalDate.of(2022, 5, 10), rentalAllDtoList.get(0).getRentedTo());
        assertEquals("Volvo", rentalAllDtoList.get(0).getCarManufacture());
        assertEquals("XC 90", rentalAllDtoList.get(0).getCarModel());
        assertEquals("Joe", rentalAllDtoList.get(0).getUserFirstName());
        assertEquals("Doe", rentalAllDtoList.get(0).getUserLastName());
        assertEquals(123, rentalAllDtoList.get(0).getUserPhoneNumber());
    }
}
