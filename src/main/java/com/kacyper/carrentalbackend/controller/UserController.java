package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.dto.UserDto;
import com.kacyper.carrentalbackend.exceptions.LoginNotFoundException;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.exceptions.WrongEmailException;
import com.kacyper.carrentalbackend.facade.UserFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping(value = "/getAllUsers")
    public List<UserDto> getAllUsers() {
        return userFacade.getAllUsers();
    }

    @GetMapping(value = "/getUser/{id}")
    public UserDto getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userFacade.getUserById(id);
    }

    @GetMapping(value = "/getByEmail/{email}")
    public UserDto getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        return userFacade.getUserByEmail(email);
    }

    @GetMapping(value = "/getByPhoneNumber/{phoneNumber}")
    public UserDto getUserByPhoneNumber(@PathVariable int phoneNumber) throws UserNotFoundException {
        return userFacade.getUserByPhoneNumber(phoneNumber);
    }

    @PostMapping(value = "/createUser")
    public UserDto createUser(@RequestBody UserDto userDto) throws WrongEmailException {
        return userFacade.saveUser(userDto);
    }

    @PutMapping(value = "/updateUser")
    public UserDto updateUser(@RequestBody UserDto userDto) throws WrongEmailException, UserNotFoundException, LoginNotFoundException {
        return userFacade.updateUser(userDto);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    void deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
    }

    @GetMapping(value = "/isUserRegistered")
    Boolean isUserRegistered(@RequestParam String email) {
        return userFacade.isUserRegistered(email);
    }
}