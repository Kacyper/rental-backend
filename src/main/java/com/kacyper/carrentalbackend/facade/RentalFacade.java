package com.kacyper.carrentalbackend.facade;

import com.kacyper.carrentalbackend.domain.Rental;
import com.kacyper.carrentalbackend.dto.RentalAllDto;
import com.kacyper.carrentalbackend.dto.RentalDto;
import com.kacyper.carrentalbackend.dto.RentalToBeExtendedDto;
import com.kacyper.carrentalbackend.exceptions.CarNotFoundException;
import com.kacyper.carrentalbackend.exceptions.RentalNotFoundException;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.mapper.RentalMapper;
import com.kacyper.carrentalbackend.repository.RentalRepository;
import com.kacyper.carrentalbackend.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RentalFacade {

    private final RentalService rentalService;

    private final RentalRepository rentalRepository;

    private final RentalMapper rentalMapper;

    private final MailToUserService mailToUserService;

    @Autowired
    public RentalFacade(RentalService rentalService, RentalRepository rentalRepository, RentalMapper rentalMapper, MailToUserService mailToUserService) {
        this.rentalService = rentalService;
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
        this.mailToUserService = mailToUserService;
    }

    public RentalAllDto getRentalById(Long id) throws RentalNotFoundException {
        return rentalMapper.mapToRentalAllDto(rentalService.getRentalById(id));
    }

    public List<RentalAllDto> getAllRentals() {
        return rentalMapper.mapToRentalAllDtoList(rentalService.getRentals());
    }

    public List<RentalAllDto> getRentalsByUsersId(Long id) {
        return rentalMapper.mapToRentalAllDtoList(rentalService.getRentalsByUserId(id));
    }

    public RentalAllDto createRent(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        Rental newRental = rentalService.createRental(rentalDto);
        mailToUserService.sendMailRegardingRental(newRental, "created");
        return rentalMapper.mapToRentalAllDto(newRental);
    }

    public RentalAllDto modifyRent(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException, RentalNotFoundException {
        Rental modifyRent = rentalService.modifyRental(rentalDto);
        mailToUserService.sendMailRegardingRental(modifyRent,"modified");
        return rentalMapper.mapToRentalAllDto(modifyRent);
    }

    public RentalAllDto extendRent(RentalToBeExtendedDto rentalToBeExtendedDto) throws RentalNotFoundException {
        Rental extendRental = rentalService.extendRental(rentalToBeExtendedDto);
        mailToUserService.sendMailRegardingRental(extendRental, "extended");
        return rentalMapper.mapToRentalAllDto(extendRental);
    }

    public void endRent(Long id) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);
        rental.setRentedTo(LocalDate.now());
        rentalService.updateDuration(rental);
        rentalService.updateCost(rental);
        mailToUserService.sendMailRegardingRental(rental, "ended");

        rentalService.endRental(id);
    }

}