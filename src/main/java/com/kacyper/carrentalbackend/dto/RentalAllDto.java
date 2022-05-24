package com.kacyper.carrentalbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalAllDto {

    private Long id;
    private LocalDate rentedFrom;
    private LocalDate rentedTo;
    private BigDecimal cost;
    private Long carId;
    private String carManufacture;
    private String carModel;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private int userPhoneNumber;
    private Long userId;
}