package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/logins")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(value = "/isLoginUsed")
    public Boolean isLoginExisting(@RequestParam String email, @RequestParam String password) {
        return loginService.isLoginExisting(email, password);
    }

}