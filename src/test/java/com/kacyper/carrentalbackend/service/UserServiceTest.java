package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.domain.User;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

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

    @DisplayName("Save User")
    @Test
    public void saveUserTest() {
        //Given
        User user = newUser();

        when(userRepository.save(user)).thenReturn(user);

        //When
        User savedUser = userService.saveUser(user);

        //Then
        Assertions.assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @DisplayName("Get User By Id")
    @Test
    public void getUserByIdTest() throws UserNotFoundException {
        //Given
        User user = newUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //When
        User userId = userService.getUserById(1L);

        //Then
        Assertions.assertEquals(user.getId(), userId.getId());
    }

    @DisplayName("Get User By Email")
    @Test
    public void getUserByEmailTest() throws UserNotFoundException {
        //Given
        User user = newUser();

        when(userRepository.findByEmail("mail")).thenReturn(Optional.of(user));

        //When
        User userMail = userService.getUserByEmail("mail");

        //Then
        Assertions.assertEquals(user.getEmail(), userMail.getEmail());
    }

    @DisplayName("Get User By Phone Number")
    @Test
    public void getUserByPhoneNumber() throws UserNotFoundException {
        //Given
        User user = newUser();

        when(userRepository.findByPhoneNumber(123)).thenReturn(Optional.of(user));

        //When
        User userPN = userService.getUserByPhoneNumber(123);

        //Then
        Assertions.assertEquals(user.getPhoneNumber(), userPN.getPhoneNumber());
    }

    @DisplayName("Get All Users")
    @Test
    public void getAllUsersTest() {
        //Given
        User user = newUser();
        List<User> userList = Collections.singletonList(user);

        when(userRepository.findAll()).thenReturn(userList);

        //When
        List<User> userList1 = userService.getAllUsers();

        //Then
        Assertions.assertNotNull(userList1);
        Assertions.assertEquals(1, userList1.size());
    }

    @DisplayName("Is User Registered")
    @Test
    public void isUserRegistered() {
        //Given
        when(userRepository.existsByEmail("mail")).thenReturn(true);

        //When
        Boolean registered = userService.isUserRegistered("mail");

        //Then
        Assertions.assertTrue(registered);
    }

    @DisplayName("Delete User")
    @Test
    public void deleteUserTest() {
        //Given
        //When
        userService.deleteUser(1L);

        //Then
        verify(userRepository, times(1)).deleteById(1L);
    }

}
