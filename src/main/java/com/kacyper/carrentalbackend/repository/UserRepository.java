package com.kacyper.carrentalbackend.repository;

import com.kacyper.carrentalbackend.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(int phoneNumber);

    Boolean existsByEmail(String email);

}