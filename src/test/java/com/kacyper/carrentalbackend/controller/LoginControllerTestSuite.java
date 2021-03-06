package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.service.LoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @DisplayName("Get If Login Exists")
    @Test
    public void shouldFetchIsLoginExistingTest() throws Exception {
        //Given
        when(loginService.isLoginExisting("mail@test.pl", "123")).thenReturn(true);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/logins/isLoginUsed/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "123")
                        .param("password", "123"))
                .andExpect(status().is(200));
    }
}
