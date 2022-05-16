package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.domain.Login;
import com.kacyper.carrentalbackend.exceptions.LoginNotFoundException;
import com.kacyper.carrentalbackend.repository.LoginRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoginServiceTestSuite {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private LoginRepository loginRepository;

    private Login sampleLogin() {
        return new Login("email", "123");
    }

    @DisplayName("Does Login Exist?")
    @Test
    public void isLoginExistingTest() {
        //Given
        when(loginRepository.existsByEmailAndPassword("email", "123")).thenReturn(true);

        //When
        Boolean ifExists = loginService.isLoginExisting("email", "123");

        //Then
        Assertions.assertTrue(ifExists);
    }

    @DisplayName("Get Login By Email And Password")
    @Test
    public void getLoginByEmailAndPasswordTest() throws LoginNotFoundException {
        //Given
        Login login = sampleLogin();

        when(loginRepository.findByEmailAndPassword("email", "123")).thenReturn(Optional.of(login));

        //When
        Login loginCheck = loginService.getLoginByEmailAndPassword("email", "123");

        //Then
        Assertions.assertEquals("email", loginCheck.getEmail());
        Assertions.assertEquals("123", loginCheck.getPassword());
    }

    @DisplayName("Save Login")
    @Test
    public void saveLoginTest() {
        //Given
        Login login = sampleLogin();

        //When
        loginService.saveLogin(login);

        //Then
        verify(loginRepository, times(1)).save(login);
    }

    @DisplayName("Delete Login")
    @Test
    public void deleteLoginTest() {
        //Given
        Login login = sampleLogin();

        //When
        loginService.deleteLogin(login);

        //Then
        verify(loginRepository, times(1)).delete(login);
    }

}