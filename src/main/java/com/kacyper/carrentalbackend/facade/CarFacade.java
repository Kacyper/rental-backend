package com.kacyper.carrentalbackend.facade;

import com.kacyper.carrentalbackend.domain.Fuel;
import com.kacyper.carrentalbackend.domain.VehicleClass;
import com.kacyper.carrentalbackend.dto.CarDto;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.mapper.CarMapper;
import com.kacyper.carrentalbackend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CarFacade {

    private final CarService carService;

    private final CarMapper carMapper;

    @Autowired
    public CarFacade(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    public CarDto saveCar(CarDto carDto) {
        return carMapper.mapToCarDto(carService.saveCar(carMapper.mapToCar(carDto)));
    }

    public CarDto getCarById(Long id) throws CarNotFoundException {
        return carMapper.mapToCarDto(carService.getCarById(id));
    }

    public CarDto getCarByVinNumber(String vin) throws CarNotFoundException {
        return carMapper.mapToCarDto(carService.getCarByVinNumber(vin));
    }

    public List<CarDto> getCars() {
        return carMapper.mapToCarDtoList(carService.getCars());
    }

    public List<CarDto> getCarByCarManufacture(String carManufacture) {
        return carMapper.mapToCarDtoList(carService.getCarByCarManufacture(carManufacture));
    }

    public List<CarDto> getCarByFuel(Fuel fuel) {
        return carMapper.mapToCarDtoList(carService.getCarByFuel(fuel));
    }

    public List<CarDto> getCarByVehicleClass(VehicleClass vehicleClass) {
        return carMapper.mapToCarDtoList(carService.getCarByVehicleClass(vehicleClass));
    }

    public List<CarDto> getCarByMileageLessThan(int distance) {
        return carMapper.mapToCarDtoList(carService.getCarByMileageLessThan(distance));
    }

    public List<CarDto> getCarByDailyCost(BigDecimal cost) {
        return carMapper.mapToCarDtoList(carService.getCarByDailyCost(cost));
    }

    public void deleteCar(Long id) {
        carService.deleteCar(id);
    }


}