package com.kacyper.carrentalbackend.facade;

import com.kacyper.carrentalbackend.domain.Car;
import com.kacyper.carrentalbackend.domain.Fuel;
import com.kacyper.carrentalbackend.domain.Status;
import com.kacyper.carrentalbackend.domain.VehicleClass;
import com.kacyper.carrentalbackend.dto.CarDto;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.mapper.CarMapper;
import com.kacyper.carrentalbackend.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CarFacadeTestSuite {

    @InjectMocks
    private CarFacade carFacade;
    @Mock
    private CarService carService;
    @Mock
    private CarMapper carMapper;

    private Car newCar() {
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

    private CarDto newCarDto() {
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

    private List<Car> initialCarList() {
        Car car = newCar();
        return Collections.singletonList(car);
    }

    private List<CarDto> initialCarDtoList() {
        CarDto carDto = newCarDto();
        return Collections.singletonList(carDto);
    }

    @DisplayName("Save Car")
    @Test
    public void saveCarTest() {
        //Given
        Car car = newCar();
        CarDto carDto = newCarDto();

        when(carMapper.mapToCar(any())).thenReturn(car);
        when(carMapper.mapToCarDto(any())).thenReturn(carDto);

        //When
        CarDto savedCar = carFacade.saveCar(carDto);
        //Then
        Assertions.assertEquals(carDto.getFuel(), savedCar.getFuel());
    }

    @DisplayName("Get Car By Id")
    @Test
    public void getCarByIdTest() throws CarNotFoundException {
        //Given
        Car car = newCar();
        CarDto carDto = newCarDto();

        when(carService.getCarById(1L)).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        //When
        CarDto carId = carFacade.getCarById(1L);

        //Then
        Assertions.assertEquals(carDto.getId(), carId.getId());
    }

    @DisplayName("Get Car By Vin Number")
    @Test
    public void getCarByVinNumberTest() throws CarNotFoundException {
        //Given
        Car car = newCar();
        CarDto carDto = newCarDto();

        when(carService.getCarByVinNumber("123123")).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        //When
        CarDto carVin = carFacade.getCarByVinNumber("123123");

        //Then
        Assertions.assertEquals(carDto.getVin(), carVin.getVin());
    }

    @DisplayName("Get All Cars")
    @Test
    public void getCarsTest() {
        //Given
        List<Car> carList = initialCarList();
        List<CarDto> carDtoList = initialCarDtoList();

        when(carService.getCars()).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When
        List<CarDto> carDtos = carFacade.getCars();

        //Then
        Assertions.assertNotNull(carDtos);
        Assertions.assertEquals(1, carDtos.size());
    }

    @DisplayName("Get Car By Manufacture")
    @Test
    public void getCarByCarManufactureTest() {
        //Given
        List<Car> carList = initialCarList();
        List<CarDto> carDtoList = initialCarDtoList();

        when(carService.getCarByCarManufacture("Volvo")).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When
        List<CarDto> carDtos = carFacade.getCarByCarManufacture("Volvo");

        //Then
        Assertions.assertNotNull(carDtos);
        Assertions.assertEquals(1, carDtos.size());

        carDtos.forEach(r -> Assertions.assertEquals(r.getCarManufacture(), carDtoList.get(0).getCarManufacture()));
    }

    @DisplayName("Get Car By Fuel")
    @Test
    public void getCarByFuelTest() {
        //Given
        List<Car> carList = initialCarList();
        List<CarDto> carDtoList = initialCarDtoList();

        when(carService.getCarByFuel(Fuel.DIESEL)).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //Then
        List<CarDto> carDtos = carFacade.getCarByFuel(Fuel.DIESEL);

        //When
        Assertions.assertNotNull(carDtos);
        Assertions.assertEquals(1, carDtos.size());

        carDtos.forEach(f -> Assertions.assertEquals(f.getFuel(), carDtoList.get(0).getFuel()));
    }

    @DisplayName("Get Car By Vehicle Class")
    @Test
    public void getCarByVehicleClassTest() {
        //Given
        List<Car> carList = initialCarList();
        List<CarDto> carDtoList = initialCarDtoList();

        when(carService.getCarByVehicleClass(VehicleClass.PREMIUM)).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //Then
        List<CarDto> carDtos = carFacade.getCarByVehicleClass(VehicleClass.PREMIUM);

        //When
        Assertions.assertNotNull(carDtos);
        Assertions.assertEquals(1, carDtos.size());

        carDtos.forEach(c -> Assertions.assertEquals(c.getVehicleClass(), carDtoList.get(0).getVehicleClass()));
    }

    @DisplayName("Get Car By Mileage Less Than")
    @Test
    public void getCarByMileageLessThanTest() {
        //Given
        List<Car> carList = initialCarList();
        List<CarDto> carDtoList = initialCarDtoList();

        when(carService.getCarByMileageLessThan(223)).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //Then
        List<CarDto> carDtos = carFacade.getCarByMileageLessThan(223);

        //When
        Assertions.assertNotNull(carDtos);
        Assertions.assertEquals(1, carDtos.size());

        carDtos.forEach(d -> Assertions.assertEquals(d.getMileage(), carDtoList.get(0).getMileage()));
    }

    @DisplayName("Get Car By Daily Cost")
    @Test
    public void getCarByDailyCostTest() {
        //Given
        List<Car> carList = initialCarList();
        List<CarDto> carDtoList = initialCarDtoList();

        when(carService.getCarByDailyCost(new BigDecimal(23))).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //Then
        List<CarDto> carDtos = carFacade.getCarByDailyCost(new BigDecimal(23));

        //When
        Assertions.assertNotNull(carDtos);
        Assertions.assertEquals(1, carDtos.size());

        carDtos.forEach(d -> Assertions.assertEquals(d.getDailyCost(), carDtoList.get(0).getDailyCost()));
    }

    @DisplayName("Delete Car")
    @Test
    public void deleteCarTest() {
        //Given
        Car car = newCar();
        CarDto carDto = newCarDto();

        when(carMapper.mapToCar(any())).thenReturn(car);
        when(carMapper.mapToCarDto(any())).thenReturn(carDto);

        CarDto savedCar = carFacade.saveCar(carDto);
        Long idCar = savedCar.getId();

        //When
        carFacade.deleteCar(idCar);

        //Then
        verify(carService, times(1)).deleteCar(idCar);
    }

}