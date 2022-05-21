package com.kacyper.carrentalbackend;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class CarRentalBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalBackendApplication.class, args);
    }

}
