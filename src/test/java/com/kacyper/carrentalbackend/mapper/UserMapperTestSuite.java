package com.kacyper.carrentalbackend.mapper;

import com.kacyper.carrentalbackend.domain.User;
import com.kacyper.carrentalbackend.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserMapperTestSuite {

    @Autowired
    private UserMapper userMapper;

    private User sampleUser() {
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

    private List<User> sampleUserList() {
        User user = sampleUser();
        return Collections.singletonList(user);
    }

    @DisplayName("Map To User")
    @Test
    public void mapToUserTest() {
        //Given
        UserDto userDto = sampleUserDto();

        //When
        User mappedUser = userMapper.mapToUser(userDto);
        mappedUser.setAccountCreationDate(LocalDate.now());

        //
        assertEquals(userDto.getFirstName(), mappedUser.getFirstName());
    }

    @DisplayName("Map To UserDto")
    @Test
    public void mapToUserDtoTest() {
        //Given
        User user = sampleUser();

        //When
        UserDto mappedUserDto = userMapper.mapToUserDto(user);
        mappedUserDto.setAccountCreationDate(LocalDate.now());

        //Then
        assertEquals(user.getFirstName(), mappedUserDto.getFirstName());
    }

    @DisplayName("Map to UserDto List")
    @Test
    public void mapToUserDtoListTest() {
        //Given
        List<User> userList = sampleUserList();

        //When
        List<UserDto> userDtoList = userMapper.mapToUserDtoList(userList);

        //Then
        assertEquals(userList.size(), userDtoList.size());
    }
}
