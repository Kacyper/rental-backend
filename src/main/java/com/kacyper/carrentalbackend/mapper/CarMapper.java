package com.kacyper.carrentalbackend.mapper;

import com.kacyper.carrentalbackend.domain.Car;
import com.kacyper.carrentalbackend.dto.CarDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {

    public Car mapToCar(final CarDto carDto) {
        return new Car(
                carDto.getId(),
                carDto.getVin(),
                carDto.getCarManufacture(),
                carDto.getModel(),
                carDto.getProductionYear(),
                carDto.getFuel(),
                carDto.getVehicleClass(),
                carDto.getMileage(),
                carDto.getDailyCost());
    }

    public CarDto mapToCarDto(final Car car) {
        return new CarDto(
                car.getId(),
                car.getVin(),
                car.getCarManufacture(),
                car.getModel(),
                car.getProductionYear(),
                car.getFuel(),
                car.getVehicleClass(),
                car.getMileage(),
                car.getDailyCost(),
                car.getStatus());
    }

    public List<CarDto> mapToCarDtoList(final List<Car> carList) {
        return carList.stream()
                .map(this::mapToCarDto)
                .collect(Collectors.toList());
    }
}
