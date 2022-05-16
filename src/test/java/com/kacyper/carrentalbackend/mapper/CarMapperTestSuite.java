package com.kacyper.carrentalbackend.mapper;

import com.kacyper.carrentalbackend.domain.Car;
import com.kacyper.carrentalbackend.domain.Fuel;
import com.kacyper.carrentalbackend.domain.Status;
import com.kacyper.carrentalbackend.domain.VehicleClass;
import com.kacyper.carrentalbackend.dto.CarDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CarMapperTestSuite {

    @Autowired
    private CarMapper carMapper;

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
                .status(Status.AVAILABLE)
                .build();
    }

    private CarDto sampleCarDto() {
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

    @DisplayName("Map To Car")
    @Test
    public void mapToCarTest() {
        //Given
        CarDto carDto = sampleCarDto();

        //When
        Car mappedCar = carMapper.mapToCar(carDto);

        //Then
        assertEquals(1L, (long) mappedCar.getId());
        assertEquals("Volvo", mappedCar.getCarManufacture());
        assertEquals(new BigDecimal(24), mappedCar.getDailyCost());
        assertEquals(Fuel.DIESEL, mappedCar.getFuel());
        assertEquals(233, mappedCar.getMileage());
        assertEquals("XC 90", mappedCar.getModel());
        assertEquals(2020, mappedCar.getProductionYear());
        assertEquals(VehicleClass.PREMIUM, mappedCar.getVehicleClass());
        assertEquals("123123", mappedCar.getVin());
        assertEquals(Status.AVAILABLE, mappedCar.getStatus());
    }

    @DisplayName("Map To CarDto")
    @Test
    public void mapToCarDtoTest() {
        //Given
        Car car = sampleCar();

        //When
        CarDto mappedCarDto = carMapper.mapToCarDto(car);

        //Then
        assertEquals(1L, (long) mappedCarDto.getId());
        assertEquals("Volvo", mappedCarDto.getCarManufacture());
        assertEquals(new BigDecimal(24), mappedCarDto.getDailyCost());
        assertEquals(Fuel.DIESEL, mappedCarDto.getFuel());
        assertEquals(233, mappedCarDto.getMileage());
        assertEquals("XC 90", mappedCarDto.getModel());
        assertEquals(2020, mappedCarDto.getProductionYear());
        assertEquals(VehicleClass.PREMIUM, mappedCarDto.getVehicleClass());
        assertEquals("123123", mappedCarDto.getVin());
        assertEquals(Status.AVAILABLE, mappedCarDto.getStatus());
    }

    @DisplayName("Map To CarDtoList")
    @Test
    public void mapToCarDtoListTest() {
        //Given
        Car car = sampleCar();
        List<Car> carList = Collections.singletonList(car);

        //When
        List<CarDto> carDtoList = carMapper.mapToCarDtoList(carList);

        //Then
        assertNotNull(carDtoList);
        assertEquals(1L, (long) carDtoList.get(0).getId());
        assertEquals("Volvo", carDtoList.get(0).getCarManufacture());
        assertEquals(new BigDecimal(24), carDtoList.get(0).getDailyCost());
        assertEquals(Fuel.DIESEL, carDtoList.get(0).getFuel());
        assertEquals(233, carDtoList.get(0).getMileage());
        assertEquals("XC 90", carDtoList.get(0).getModel());
        assertEquals(2020, carDtoList.get(0).getProductionYear());
        assertEquals(VehicleClass.PREMIUM, carDtoList.get(0).getVehicleClass());
        assertEquals("123123", carDtoList.get(0).getVin());
        assertEquals(Status.AVAILABLE, carDtoList.get(0).getStatus());
    }

}
