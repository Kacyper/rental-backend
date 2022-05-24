package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.domain.User;
import com.kacyper.carrentalbackend.dto.UserDto;
import com.kacyper.carrentalbackend.exceptions.UserNotFoundException;
import com.kacyper.carrentalbackend.mapper.UserMapper;
import com.kacyper.carrentalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

//    public User saveUser(final UserDto userDto) {
//        return userRepository.save(userMapper.mapToUser(userDto));
//    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByPhoneNumber(int phoneNumber) throws UserNotFoundException {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Boolean isUserRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

}