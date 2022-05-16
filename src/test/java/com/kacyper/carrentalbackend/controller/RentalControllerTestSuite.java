package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.dto.RentalAllDto;
import com.kacyper.carrentalbackend.facade.RentalFacade;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RentalController.class)
public class RentalControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalFacade rentalFacade;

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

    @DisplayName("Find Rent By Id")
    @Test
    public void shouldFetchRentalById() throws Exception {
        //Given
        RentalAllDto rentalAllDto = newRentalAllDto();

        when(rentalFacade.getRentalById(1L)).thenReturn(rentalAllDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/rentals/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carManufacture", Matchers.is("Volvo")));
    }

    @DisplayName("Find All Rentals")
    @Test
    public void shouldFetchAllRentals() throws Exception {
        //Given
        RentalAllDto rentalAllDto = newRentalAllDto();
        List<RentalAllDto> rentalAllDtoList = Collections.singletonList(rentalAllDto);

        when(rentalFacade.getAllRentals()).thenReturn(rentalAllDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/rentals/getAllRentals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userEmail", Matchers.is("mail")));
    }

    @DisplayName("Get Rental By User's ID")
    @Test
    public void shouldFetchRentalsByUsersId() throws Exception {
        //Given
        RentalAllDto rentalAllDto = newRentalAllDto();
        List<RentalAllDto> rentalAllDtoList = Collections.singletonList(rentalAllDto);

        when(rentalFacade.getRentalsByUsersId(1L)).thenReturn(rentalAllDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/rentals/byUserId/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userEmail", Matchers.is("mail")));
    }

    @DisplayName("End Rent")
    @Test
    public void shouldEndRent() throws Exception {
        //Given
        doNothing().when(rentalFacade).endRent(anyLong());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/rentals/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}
