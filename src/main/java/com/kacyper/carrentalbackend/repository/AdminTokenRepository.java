package com.kacyper.carrentalbackend.repository;

import com.kacyper.carrentalbackend.domain.AdminToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminTokenRepository extends CrudRepository<AdminToken, Long> {

    @Override
    List<AdminToken> findAll();
    Boolean existsByToken(String token);
}