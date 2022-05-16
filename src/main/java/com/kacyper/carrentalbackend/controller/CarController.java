package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.domain.Fuel;
import com.kacyper.carrentalbackend.domain.VehicleClass;
import com.kacyper.carrentalbackend.dto.CarDto;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.facade.CarFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/cars")
public class CarController {

    private final CarFacade carFacade;

    @Autowired
    public CarController(CarFacade carFacade) {
        this.carFacade = carFacade;
    }

    @GetMapping(value = "/byId/{id}")
    public CarDto getCarById(@PathVariable Long id) throws CarNotFoundException {
        return carFacade.getCarById(id);
    }

    @GetMapping(value = "/byVin/{vin}")
    public CarDto getCarByVinNumber(@PathVariable String vin) throws CarNotFoundException {
        return carFacade.getCarByVinNumber(vin);
    }

    @GetMapping(value = "/getCars")
    public List<CarDto> getAllCars() {
        return carFacade.getCars();
    }

    @GetMapping(value = "/carByMileageLessThan/{distance}")
    public List<CarDto> getCarByMileageLessThan(@PathVariable int distance) {
        return carFacade.getCarByMileageLessThan(distance);
    }

    @GetMapping(value = "/carByDailyCost/{cost}")
    public List<CarDto> getCarByDailyCost(@PathVariable BigDecimal cost) {
        return carFacade.getCarByDailyCost(cost);
    }

    @GetMapping(value = "/brand/")
    public List<CarDto> getCarByCarManufacture(@RequestParam String carManufacture) {
        return carFacade.getCarByCarManufacture(carManufacture);
    }

    @GetMapping(value = "/fuel")
    public List<CarDto> getCarByFuel(@RequestParam String fuel) {
        return carFacade.getCarByFuel(Fuel.valueOf(fuel));
    }

    @GetMapping(value = "/class")
    public List<CarDto> getCarByVehicleClass(@RequestParam String vehicleClass) {
        return carFacade.getCarByVehicleClass(VehicleClass.valueOf(vehicleClass));
    }

    @PostMapping(value = "/addCar")
    public CarDto addCar(@RequestBody CarDto carDto) {
        return carFacade.saveCar(carDto);
    }

    @PutMapping(value = "/updateCar")
    public CarDto updateCar(@RequestBody CarDto carDto) {
        return carFacade.saveCar(carDto);
    }

    @PatchMapping(value = "/modifyCar")
    public CarDto modifyCar(@RequestBody CarDto carDto) {
        return carFacade.saveCar(carDto);
    }

    @DeleteMapping(value = "/{id}")
    void deleteCar(@PathVariable Long id) {
        carFacade.deleteCar(id);
    }

}
