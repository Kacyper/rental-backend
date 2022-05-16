package com.kacyper.carrentalbackend.controller;

import com.google.gson.Gson;
import com.kacyper.carrentalbackend.domain.Fuel;
import com.kacyper.carrentalbackend.domain.Status;
import com.kacyper.carrentalbackend.domain.VehicleClass;
import com.kacyper.carrentalbackend.dto.CarDto;
import com.kacyper.carrentalbackend.facade.CarFacade;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
class CarControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarFacade carFacade;

    private CarDto sampleCar() {
        return CarDto.builder()
                .id(1L)
                .carManufacture("Volvo")
                .dailyCost(new BigDecimal(24))
                .fuel(Fuel.DIESEL)
                .mileage(233)
                .model("XC 90")
                .productionYear(2020)
                .vehicleClass(VehicleClass.PREMIUM)
                .vin("123123")
                .status(Status.AVAILABLE)
                .build();
    }

    private List<CarDto> sampleCarList() {
        CarDto carDto = sampleCar();
        return Collections.singletonList(carDto);
    }

    @DisplayName("Get Car By Id")
    @Test
    public void shouldFetchCarByIdTest() throws Exception {
        //Given
        CarDto carDto = sampleCar();

        when(carFacade.getCarById(1L)).thenReturn(carDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cars/byId/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carManufacture", Matchers.is("Volvo")));
    }

    @DisplayName("Get Car By Vin Number")
    @Test
    public void shouldFetchCarByVinNumberTest() throws Exception {
        //Given
        CarDto carDto = sampleCar();

        when(carFacade.getCarByVinNumber("123123")).thenReturn(carDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cars/byVin/123123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("123123")));
    }

    @DisplayName("Get All Cars")
    @Test
    public void shouldFetchAllCarsTest() throws Exception {
        //Given
        List<CarDto> carDtoList = sampleCarList();

        when(carFacade.getCars()).thenReturn(carDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cars/getCars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("123123")));
    }

    @DisplayName("Get Car By Mileage Less Than")
    @Test
    public void shouldFetchCarByMileageLessThan() throws Exception {
        //Given
        List<CarDto> carDtoList = sampleCarList();

        when(carFacade.getCarByMileageLessThan(233)).thenReturn(carDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cars/carByMileageLessThan/233")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("distance", String.valueOf(233)))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileage", Matchers.is(233)));
    }

    @DisplayName("Get Car By Daily Cost")
    @Test
    public void shouldFetchCarByDailyCost() throws Exception {
        //Given
        List<CarDto> carDtoList = sampleCarList();

        when(carFacade.getCarByDailyCost(new BigDecimal(24))).thenReturn(carDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cars/carByDailyCost/24")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("cost", String.valueOf(24)))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dailyCost", Matchers.is(24)));
    }

    @DisplayName("Get Car By Brand")
    @Test
    public void shouldFetchCarByCarManufactureTest() throws Exception {
        //Given
        List<CarDto> carDtoList = sampleCarList();

        when(carFacade.getCarByCarManufacture("Volvo")).thenReturn(carDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cars/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("carManufacture", "Volvo"))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carManufacture", Matchers.is("Volvo")));
    }

    @DisplayName("Get Car By Fuel")
    @Test
    public void shouldFetchCarByFuelTest() throws Exception {
        //Given
        List<CarDto> carDtoList = sampleCarList();

        when(carFacade.getCarByFuel(Fuel.DIESEL)).thenReturn(carDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cars/fuel/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fuel", "DIESEL"))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fuel", Matchers.is("DIESEL")));
    }

    @DisplayName("Get Car By Class")
    @Test
    public void shouldFetchCarByVehicleClassTest() throws Exception {
        //Given
        List<CarDto> carDtoList = sampleCarList();

        when(carFacade.getCarByVehicleClass(VehicleClass.PREMIUM)).thenReturn(carDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/cars/class/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("vehicleClass", "PREMIUM"))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vehicleClass", Matchers.is("PREMIUM")));
    }

    @DisplayName("Post Add A New Car")
    @Test
    public void shouldAddCarTest() throws Exception {
        //Given
        CarDto carDto = sampleCar();

        when(carFacade.saveCar(any(CarDto.class))).thenReturn(carDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(carDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/cars/addCar/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicleClass", Matchers.is("PREMIUM")));
    }

    @DisplayName("Put Update A Car")
    @Test
    public void shouldUpdateCarTest() throws Exception {
        //Given
        CarDto carDto = sampleCar();

        when(carFacade.saveCar(any(CarDto.class))).thenReturn(carDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(carDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/cars/updateCar/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicleClass", Matchers.is("PREMIUM")));
    }

    @DisplayName("Patch Modify A Car")
    @Test
    public void shouldModifyCarTest() throws Exception {
        //Given
        CarDto carDto = sampleCar();

        when(carFacade.saveCar(any(CarDto.class))).thenReturn(carDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(carDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .patch("/v1/cars/modifyCar/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicleClass", Matchers.is("PREMIUM")));
    }

    @DisplayName("Delete A Car")
    @Test
    public void shouldDeleteCarTest() throws Exception {
        //Given
        doNothing().when(carFacade).deleteCar(anyLong());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/cars/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

}