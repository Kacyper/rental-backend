package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.dto.UserDto;
import com.kacyper.carrentalbackend.facade.UserFacade;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    private UserDto sampleUserDto() {
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

    private List<UserDto> sampleUserDtoList() {
        UserDto userDto = sampleUserDto();
        return Collections.singletonList(userDto);
    }

    @DisplayName("Get All Users")
    @Test
    public void shouldFetchAllUsers() throws Exception {
        //Given
        List<UserDto> userDtoList = sampleUserDtoList();

        when(userFacade.getAllUsers()).thenReturn(userDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users/getAllUsers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Joe")));
    }

    @DisplayName("Get User By Id")
    @Test
    public void shouldFetchUserByIdTest() throws Exception {
        //Given
        UserDto userDto = sampleUserDto();

        when(userFacade.getUserById(1L)).thenReturn(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users/getUser/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Joe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("user@mail.com")));
    }

    @DisplayName("Get User By Email")
    @Test
    public void shouldFetchUserByEmailTest() throws Exception {
        //Given
        UserDto userDto = sampleUserDto();

        when(userFacade.getUserByEmail("user@mail.com")).thenReturn(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users/getByEmail/user@mail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Joe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("user@mail.com")));
    }

    @DisplayName("Get User By Phone Number")
    @Test
    public void shouldFetchUserByPhoneNumberTest() throws Exception {
        //Given
        UserDto userDto = sampleUserDto();

        when(userFacade.getUserByPhoneNumber(123)).thenReturn(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users/getByPhoneNumber/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Joe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("user@mail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(123)));
    }

    @DisplayName("Get If User Is Registered")
    @Test
    public void shouldFetchIsUserRegisteredTest() throws Exception {
        //Given
        when(userFacade.isUserRegistered("user@mail.com")).thenReturn(true);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users/isUserRegistered/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "user@mail.com"))
                .andExpect(status().is(200));
    }

    @DisplayName("Delete User")
    @Test
    public void shouldDeleteUserTest() throws Exception {
        //Given
        doNothing().when(userFacade).deleteUser(anyLong());
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/users/deleteUser/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .param("id", "1"))
                .andExpect(status().is(200));
    }
}
