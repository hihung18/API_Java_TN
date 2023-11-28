package com.example.api_java.controller;

import com.example.api_java.model.dto.ChangePassByEmailRequest;
import com.example.api_java.model.dto.ChangePassRequest;
import com.example.api_java.model.dto.LoginRequest;
import com.example.api_java.model.dto.SignupRequest;
import com.example.api_java.service.impl.UserDetailServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600) //cho phép http, get post..
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserDetailServiceImpl service;

    public AuthController(UserDetailServiceImpl service) {

        this.service = service;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return service.checkLogin(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        return service.register(signUpRequest);
    }

    @PostMapping("/changePass")
    public ResponseEntity<?> changePass(@Valid @RequestBody ChangePassRequest signUpRequest) {

        return service.changePass(signUpRequest);
    }

    @PostMapping("/changePassByEmail")
    public ResponseEntity<?> changePassByEmail(@Valid @RequestBody ChangePassByEmailRequest signUpRequest) {

        return service.changePassByEmail(signUpRequest);
    }
}
