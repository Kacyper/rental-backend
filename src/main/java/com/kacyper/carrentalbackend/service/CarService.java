package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.domain.Car;
import com.kacyper.carrentalbackend.domain.Fuel;
import com.kacyper.carrentalbackend.domain.VehicleClass;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;


@Transactional
@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Car getCarById(Long id) throws CarNotFoundException {
        return carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    public Car getCarByVinNumber(String vin) throws CarNotFoundException {
        return carRepository.findByVin(vin).orElseThrow(CarNotFoundException::new);
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public List<Car> getCarByCarManufacture(String carManufacture) {
        return carRepository.findAllByCarManufacture(carManufacture);
    }

    public List<Car> getCarByFuel(Fuel fuel) {
        return carRepository.findAllByFuel(fuel);
    }

    public List<Car> getCarByVehicleClass(VehicleClass vehicleClass) {
        return carRepository.findAllByVehicleClass(vehicleClass);
    }

    public List<Car> getCarByMileageLessThan(int distance) {
        return carRepository.findAllByMileageLessThan(distance);
    }

    public List<Car> getCarByDailyCost(BigDecimal cost) {
        return carRepository.findAllByDailyCostLessThan(cost);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }


}
