package com.kacyper.carrentalbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalToBeExtendedDto {

    private Long rentalId;
    private Integer extension;

}
