package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.domain.Car;
import com.kacyper.carrentalbackend.domain.Fuel;
import com.kacyper.carrentalbackend.domain.VehicleClass;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CarServiceTestSuite {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

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

    private List<Car> newCarList() {
        Car car1 = Car.builder()
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

        Car car2 = Car.builder()
                .id(2L)
                .carManufacture("Volvo")
                .dailyCost(new BigDecimal(25))
                .fuel(Fuel.GASOLINE)
                .mileage(123)
                .model("XC 60")
                .productionYear(2021)
                .vehicleClass(VehicleClass.STANDARD_SUV)
                .vin("123123")
                .build();

        return Arrays.asList(car1, car2);
    }

    @DisplayName("Save Car")
    @Test
    public void saveCarTest() {
        //Given
        Car car = newCar();
        when(carRepository.save(car)).thenReturn(car);

        //When
        Car savedCar = carService.saveCar(car);

        //Then
        Assertions.assertEquals(car.getId(), savedCar.getId());
    }

    @DisplayName("Get Car By Id")
    @Test
    public void getCarByIdTest() throws CarNotFoundException {
        //Given
        Car car = newCar();
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        //When
        Car carId = carService.getCarById(1L);

        //Then
        Assertions.assertEquals(car.getId(), carId.getId());
    }

    @DisplayName("Get Car By Vin Number")
    @Test
    public void getCarByVinNumberTest() throws CarNotFoundException {
        //Given
        Car car = newCar();
        when(carRepository.findByVin("123")).thenReturn(Optional.of(car));

        //When
        Car carVin = carService.getCarByVinNumber("123");

        //Then
        Assertions.assertEquals(car.getVin(), carVin.getVin());
    }

    @DisplayName("Get All Cars")
    @Test
    public void getCarsTest() {
        //Given
        List<Car> carList = newCarList();
        when(carRepository.findAll()).thenReturn(carList);

        //When
        List<Car> carsList = carService.getCars();

        //Then
        Assertions.assertNotNull(carsList);
        Assertions.assertEquals(2, carsList.size());
    }

    @DisplayName("Get Car By Car Manufacture")
    @Test
    public void getCarByCarManufactureTest() {
        //Given
        List<Car> carList = newCarList();
        when(carRepository.findAllByCarManufacture("Volvo")).thenReturn(carList);

        //When
        List<Car> carsList = carService.getCarByCarManufacture("Volvo");

        //Then
        Assertions.assertNotNull(carsList);
        Assertions.assertEquals(2, carsList.size());
        Assertions.assertEquals(carList.get(0).getCarManufacture(), carsList.get(0).getCarManufacture());
        Assertions.assertEquals(carList.get(1).getCarManufacture(), carsList.get(1).getCarManufacture());
    }

    @DisplayName("Get Car By Fuel")
    @Test
    public void getCarByFuelTest() {
        //Given
        List<Car> carList = newCarList();
        when(carRepository.findAllByFuel(Fuel.DIESEL)).thenReturn(carList);

        //When
        List<Car> carsList = carService.getCarByFuel(Fuel.DIESEL);

        //
        Assertions.assertNotNull(carsList);
        Assertions.assertEquals(2, carsList.size());
        Assertions.assertEquals(carList.get(0).getFuel(), carsList.get(0).getFuel());
        Assertions.assertEquals(carList.get(1).getFuel(), carsList.get(1).getFuel());
    }

    @DisplayName("Get Car By Vehicle Class")
    @Test
    public void getCarByVehicleClassTest() {
        //Given
        List<Car> carList = newCarList();
        when(carRepository.findAllByVehicleClass(VehicleClass.PREMIUM)).thenReturn(carList);

        //When
        List<Car> carsList = carService.getCarByVehicleClass(VehicleClass.PREMIUM);

        //
        Assertions.assertNotNull(carsList);
        Assertions.assertEquals(2, carsList.size());
        Assertions.assertEquals(carList.get(0).getVehicleClass(), carsList.get(0).getVehicleClass());
        Assertions.assertEquals(carList.get(1).getVehicleClass(), carsList.get(1).getVehicleClass());
    }

    @DisplayName("Get Car By Mileage Less Than")
    @Test
    public void getCarByMileageLessThanTest() {
        //Given
        List<Car> carList = newCarList();
        when(carRepository.findAllByMileageLessThan(400)).thenReturn(carList);

        //When
        List<Car> carsList = carService.getCarByMileageLessThan(400);

        //
        Assertions.assertNotNull(carsList);
        Assertions.assertEquals(2, carsList.size());
        Assertions.assertEquals(carList.get(0).getMileage(), carsList.get(0).getMileage());
        Assertions.assertEquals(carList.get(1).getMileage(), carsList.get(1).getMileage());
    }

    @DisplayName("Get Car By Daily Cost")
    @Test
    public void getCarByDailyCostTest() {
        //Given
        List<Car> carList = newCarList();
        when(carRepository.findAllByDailyCostLessThan(new BigDecimal(42))).thenReturn(carList);

        //When
        List<Car> carsList = carService.getCarByDailyCost(new BigDecimal(42));

        //
        Assertions.assertNotNull(carsList);
        Assertions.assertEquals(2, carsList.size());
        Assertions.assertEquals(carList.get(0).getDailyCost(), carsList.get(0).getDailyCost());
        Assertions.assertEquals(carList.get(1).getDailyCost(), carsList.get(1).getDailyCost());
    }

    @DisplayName("Delete Car")
    @Test
    public void deleteCarTest() {
        //Given
        //When
        carRepository.deleteById(1L);

        //Then
        verify(carRepository, times(1)).deleteById(1L);
    }

}