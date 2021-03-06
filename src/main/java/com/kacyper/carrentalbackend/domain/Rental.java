package com.kacyper.carrentalbackend.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RENTALS")
public class Rental {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "RENTED_FROM")
    private LocalDate rentedFrom;

    @NotNull
    @Column(name = "RENTED_TO")
    private LocalDate rentedTo;

    @NotNull
    @Column(name = "DURATION")
    private Long duration;

    @NotNull
    @Column(name = "COST")
    private BigDecimal cost;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CAR_ID")
    private Car car;

    public Rental(LocalDate rentedFrom, LocalDate rentedTo, User user, Car car) {
        this.rentedFrom = rentedFrom;
        this.rentedTo = rentedTo;
        this.duration = DAYS.between(rentedFrom, rentedTo);
        this.cost = car.getDailyCost().multiply(new BigDecimal(duration));
        this.user = user;
        this.car = car;
    }

    public Rental(LocalDate rentedFrom, LocalDate rentedTo, Car car) {
    }
}