package com.kacyper.carrentalbackend.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CARS")
public class Car {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "VIN_NUMBER", unique = true)
    private String vin;

    @NotNull
    @Column(name = "CAR_MANUFACTURE")
    private String carManufacture;

    @NotNull
    @Column(name = "MODEL")
    private String model;

    @NotNull
    @Column(name = "PRODUCTION_YEAR")
    private int productionYear;

    @Enumerated
    @Column(name = "FUEL_TYPE")
    private Fuel fuel;

    @Enumerated
    @Column(name = "VEHICLE_CLASS")
    private VehicleClass vehicleClass;

    @NotNull
    @Column(name = "MILEAGE")
    private int mileage;

    @NotNull
    @Column(name = "DAILY_COST")
    private BigDecimal dailyCost;

    @Enumerated
    @Column(name = "STATUS")
    private Status status;

    @OneToMany(targetEntity = Rental.class, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.EAGER,
            mappedBy = "car")
    private List<Rental> rentalList = new ArrayList<>();

    public Car(Long id, String vin, String carManufacture, String model, int productionYear, Fuel fuel, VehicleClass vehicleClass, int mileage, BigDecimal dailyCost) {
        this.id = id;
        this.vin = vin;
        this.carManufacture = carManufacture;
        this.model = model;
        this.productionYear = productionYear;
        this.fuel = fuel;
        this.vehicleClass = vehicleClass;
        this.mileage = mileage;
        this.dailyCost = dailyCost;
        this.status = Status.AVAILABLE;
    }
}
