package com.kacyper.carrentalbackend.mapper;

import com.kacyper.carrentalbackend.domain.Rental;
import com.kacyper.carrentalbackend.dto.RentalAllDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    public RentalAllDto mapToRentalAllDto(final Rental rental) {
        return new RentalAllDto(
                rental.getId(),
                rental.getRentedFrom(),
                rental.getRentedTo(),
                rental.getCost(),
                rental.getCar().getId(),
                rental.getCar().getCarManufacture(),
                rental.getCar().getModel(),
                rental.getUser().getFirstName(),
                rental.getUser().getLastName(),
                rental.getUser().getEmail(),
                rental.getUser().getPhoneNumber(),
                rental.getUser().getId());
    }

    public List<RentalAllDto> mapToRentalAllDtoList(final List<Rental> rentalList) {
        return rentalList.stream()
                .map(this::mapToRentalAllDto)
                .collect(Collectors.toList());
    }
}