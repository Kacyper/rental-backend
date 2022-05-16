package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.dto.RentalAllDto;
import com.kacyper.carrentalbackend.dto.RentalDto;
import com.kacyper.carrentalbackend.dto.RentalToBeExtendedDto;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.exceptions.RentalNotFoundException;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.facade.RentalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/rentals")
public class RentalController {

    private final RentalFacade rentalFacade;

    @Autowired
    public RentalController(RentalFacade rentalFacade) {
        this.rentalFacade = rentalFacade;
    }

    @GetMapping(value = "/{id}")
    public RentalAllDto getRentalById(@PathVariable Long id) throws RentalNotFoundException {
        return rentalFacade.getRentalById(id);
    }

    @GetMapping(value = "/getAllRentals")
    public List<RentalAllDto> getAllRentals() {
        return rentalFacade.getAllRentals();
    }

    @GetMapping(value = "/byUserId/{id}")
    public List<RentalAllDto> getRentalsByUsersId(@PathVariable Long id) {
        return rentalFacade.getRentalsByUsersId(id);
    }

    @PostMapping(value = "/createRent")
    public RentalAllDto createRent(@RequestBody RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        return rentalFacade.createRent(rentalDto);
    }

    @PutMapping(value = "/modifyRent")
    public RentalAllDto modifyRent(@RequestBody RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException, RentalNotFoundException {
        return rentalFacade.modifyRent(rentalDto);
    }

    @PutMapping(value = "/extendRent")
    public RentalAllDto extendRent(@RequestBody RentalToBeExtendedDto rentalToBeExtendedDto) throws RentalNotFoundException {
        return rentalFacade.extendRent(rentalToBeExtendedDto);
    }

    @DeleteMapping(value = "/{id}")
    public void endRent(@PathVariable Long id) throws RentalNotFoundException {
        rentalFacade.endRent(id);
    }
}