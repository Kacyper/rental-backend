package com.kacyper.carrentalbackend.repository;

import com.kacyper.carrentalbackend.domain.Car;
import com.kacyper.carrentalbackend.domain.Fuel;
import com.kacyper.carrentalbackend.domain.VehicleClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    @Override
    List<Car> findAll();

    List<Car> findAllByCarManufacture(String carManufacture);

    List<Car> findAllByFuel(Fuel fuel);

    List<Car> findAllByVehicleClass(VehicleClass vehicleClass);

    List<Car> findAllByMileageLessThan(int maxDistance);

    List<Car> findAllByDailyCostLessThan(BigDecimal maxCost);

    Optional<Car> findByVin(String vin);

}
