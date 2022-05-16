package com.kacyper.carrentalbackend.dto;

import com.kacyper.carrentalbackend.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private Long id;
    private String vin;
    private String carManufacture;
    private String model;
    private int productionYear;
    private Fuel fuel;
    private VehicleClass vehicleClass;
    private int mileage;
    private BigDecimal dailyCost;
    private Status status;
}
