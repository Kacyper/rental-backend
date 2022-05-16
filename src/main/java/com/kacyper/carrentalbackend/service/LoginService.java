package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.domain.Login;
import com.kacyper.carrentalbackend.exceptions.LoginNotFoundException;
import com.kacyper.carrentalbackend.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Boolean isLoginExisting(String email, String password) {
        return loginRepository.existsByEmailAndPassword(email, password);
    }

    public Login getLoginByEmailAndPassword(String email, String password) throws LoginNotFoundException {
        return loginRepository.findByEmailAndPassword(email, password).orElseThrow(LoginNotFoundException::new);
    }

    public void saveLogin(Login login) {
        loginRepository.save(login);
    }

    public void deleteLogin(Login login) {
        loginRepository.delete(login);
    }

}