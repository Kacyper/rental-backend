package com.kacyper.carrentalbackend.facade;

import com.kacyper.carrentalbackend.domain.User;
import com.kacyper.carrentalbackend.dto.UserDto;
import com.kacyper.carrentalbackend.exceptions.LoginNotFoundException;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.exceptions.WrongEmailException;
import com.kacyper.carrentalbackend.mapper.UserMapper;
import com.kacyper.carrentalbackend.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginService loginService;
    private final MailVerificationService mailVerificationService;
    private final MailToUserService mailToUserService;

    public UserFacade(UserService userService, UserMapper userMapper, LoginService loginService, MailVerificationService mailVerificationService, MailToUserService mailToUserService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.loginService = loginService;
        this.mailVerificationService = mailVerificationService;
        this.mailToUserService = mailToUserService;
    }

    public List<UserDto> getAllUsers() {
        return userMapper.mapToUserDtoList(userService.getAllUsers());
    }

    public UserDto getUserById(final Long id) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.getUserById(id));
    }

    public UserDto getUserByEmail(final String email) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.getUserByEmail(email));
    }

    public UserDto getUserByPhoneNumber(final int phoneNumber) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.getUserByPhoneNumber(phoneNumber));
    }

    public UserDto saveUser(final UserDto userDto) throws WrongEmailException {
        if (isMailExisting(userDto.getEmail())) {
            saveLogin(userDto);
            User user = userMapper.mapToUser(userDto);
            user.setAccountCreationDate(LocalDate.now());
            User savedUser = userService.saveUser(user);
            mailToUserService.sendMailCreatingUser(savedUser);
            return userMapper.mapToUserDto(savedUser);
        } else {
            throw new WrongEmailException();
        }
    }

    public UserDto updateUser(final UserDto userDto) throws WrongEmailException, LoginNotFoundException, UserNotFoundException {
        if (isMailInDB(userDto.getEmail())) {
            updateLogin(userDto);
            User user = userMapper.mapToUser(userDto);
            user.setAccountCreationDate(LocalDate.now());
            return userMapper.mapToUserDto(userService.saveUser(user));
        }else {
            throw new WrongEmailException();
        }
    }

    public void deleteUser(final Long id) throws UserNotFoundException, LoginNotFoundException {
        deleteLogin(id);
        userService.deleteUser(id);
    }

    public Boolean isUserRegistered(String email) {
        return userService.isUserRegistered(email);
    }

    public boolean isMailExisting(String email) {
        MailVerifierDto mailVerifierDto = mailVerificationService.verifyMail(email);
        return mailVerifierDto.isSmtpCheck();
    }

    public Boolean isMailInDB(String email) {
        return userService.isUserRegistered(email);
    }

    public void saveLogin(UserDto userDto) {
        loginService.saveLogin(new Login(
                userDto.getEmail(),
                userDto.getPassword()
        ));
    }

    public void updateLogin(UserDto userDto) throws LoginNotFoundException, UserNotFoundException {
        User oldUser = userService.getUserById(userDto.getId());
        Login oldUserLogin = loginService.getLoginByEmailAndPassword(oldUser.getEmail(), oldUser.getPassword());

        oldUserLogin.setEmail(userDto.getEmail());
        oldUserLogin.setPassword(userDto.getPassword());
    }

    public void deleteLogin(Long id) throws UserNotFoundException, LoginNotFoundException {
        User user = userService.getUserById(id);
        Login login = loginService.getLoginByEmailAndPassword(user.getEmail(), user.getPassword());
        loginService.deleteLogin(login);
    }
}
