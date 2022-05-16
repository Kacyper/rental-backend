package com.kacyper.carrentalbackend.facade;

import com.kacyper.carrentalbackend.domain.Login;
import com.kacyper.carrentalbackend.domain.User;
import com.kacyper.carrentalbackend.dto.UserDto;
import com.kacyper.carrentalbackend.dto.api.mail.MailVerifierDto;
import com.kacyper.carrentalbackend.exceptions.LoginNotFoundException;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.exceptions.WrongEmailException;
import com.kacyper.carrentalbackend.mapper.UserMapper;
import com.kacyper.carrentalbackend.service.LoginService;
import com.kacyper.carrentalbackend.service.UserService;
import com.kacyper.carrentalbackend.service.mailService.MailToUserService;
import com.kacyper.carrentalbackend.service.mailService.MailVerificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserFacadeTestSuite {

    @InjectMocks
    private UserFacade userFacade;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private LoginService loginService;

    @Mock
    private MailVerificationService mailVerificationService;

    @Mock
    private MailToUserService mailToUserService;


    private User newUser() {
        return User.builder()
                .id(1L)
                .email("user@mail.com")
                .password("pass")
                .firstName("Joe")
                .lastName("Doe")
                .phoneNumber(123)
                .accountCreationDate(LocalDate.now())
                .build();
    }

    private UserDto newUserDto() {
        return UserDto.builder()
                .id(1L)
                .email("user@mail.com")
                .password("pass")
                .firstName("Joe")
                .lastName("Doe")
                .phoneNumber(123)
                .accountCreationDate(LocalDate.now())
                .build();
    }

    private List<User> newUserList() {
        User user = newUser();
        return Collections.singletonList(user);
    }

    private List<UserDto> newUserDtoList() {
        UserDto userDto = newUserDto();
        return Collections.singletonList(userDto);
    }

    private MailVerifierDto newMailVerifierDto() {
        return new MailVerifierDto(true);
    }

    @DisplayName("Save User")
    @Test
    public void saveUserTest() throws WrongEmailException {
        //Given
        User user = newUser();
        UserDto userDto = newUserDto();
        MailVerifierDto mailVerifierDto = newMailVerifierDto();

        when(mailVerificationService.verifyMail(anyString())).thenReturn(mailVerifierDto);
        when(userMapper.mapToUser(any())).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        doNothing().when(mailToUserService).sendMailCreatingUser(user);
        doNothing().when(loginService).saveLogin(ArgumentMatchers.any());

        //When
        UserDto savedUser = userFacade.saveUser(userDto);

        //Then
        Assertions.assertEquals(userDto.getFirstName(), savedUser.getFirstName());
    }

    @DisplayName("Get All Users")
    @Test
    public void getAllUsersTest() {
        //Given
        List<User> userList = newUserList();
        List<UserDto> userDtoList = newUserDtoList();

        when(userService.getAllUsers()).thenReturn(userList);
        when(userMapper.mapToUserDtoList(userList)).thenReturn(userDtoList);

        //When
        List<UserDto> userDtos = userFacade.getAllUsers();

        //Then
        Assertions.assertNotNull(userDtos);
        Assertions.assertEquals(1, userDtos.size());
    }

    @DisplayName("Get User By ID")
    @Test
    public void getUserByIdTest() throws UserNotFoundException {
        //Given
        User user = newUser();
        UserDto userDto = newUserDto();

        when(userService.getUserById(1L)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        //When
        UserDto userId = userFacade.getUserById(1L);

        //Then
        Assertions.assertEquals(userDto.getId(), userId.getId());
    }

    @DisplayName("Get User By Email")
    @Test
    public void getUserByEmailTest() throws UserNotFoundException {
        //Given
        User user = newUser();
        UserDto userDto = newUserDto();

        when(userService.getUserByEmail("email")).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        //When
        UserDto userEmail = userFacade.getUserByEmail("email");

        //Then
        Assertions.assertEquals(userDto.getEmail(), userEmail.getEmail());
    }

    @DisplayName("Get User By Phone Number")
    @Test
    public void getUserByPhoneNumber() throws UserNotFoundException {
        //Given
        User user = newUser();
        UserDto userDto = newUserDto();

        when(userService.getUserByPhoneNumber(123)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        //When
        UserDto userPhoneNumber = userFacade.getUserByPhoneNumber(123);

        //Then
        Assertions.assertEquals(userDto.getPhoneNumber(), userPhoneNumber.getPhoneNumber());
    }

    @DisplayName("Delete User")
    @Test
    public void deleteUserTest() throws UserNotFoundException, LoginNotFoundException, WrongEmailException {
        //Given
        User user = newUser();
        Login login = new Login("mail@mail", "123");

        when(userService.getUserById(anyLong())).thenReturn(user);
        when(loginService.getLoginByEmailAndPassword(anyString(), anyString())).thenReturn(login);
        doNothing().when(loginService).deleteLogin(any());

        //When
        userFacade.deleteUser(1L);

        //Then
        verify(userService, times(1)).deleteUser(1L);
    }

    @DisplayName("Is User Registered?")
    @Test
    public void isUserRegisteredTest() {
        //Given
        when(userService.isUserRegistered("email")).thenReturn(true);

        //When
        boolean result = userFacade.isUserRegistered("email");

        //Then
        Assertions.assertTrue(result);
    }

    @DisplayName("Is Mail Valid")
    @Test
    public void isMailExistingTest() {
        MailVerifierDto mailVerifierDto = newMailVerifierDto();

        when(mailVerificationService.verifyMail("email")).thenReturn(mailVerifierDto);

        //When
        boolean mailTest = userFacade.isMailExisting("email");

        //Then
        Assertions.assertTrue(mailTest);
    }
}