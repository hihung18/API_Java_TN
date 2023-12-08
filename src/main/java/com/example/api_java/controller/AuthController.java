package com.example.api_java.controller;

import com.example.api_java.model.dto.*;
import com.example.api_java.service.impl.UserDetailServiceImpl;
import com.google.api.client.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600) //cho phép http, get post..
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserDetailServiceImpl service;
    private static final String FCM_API = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "YOUR_SERVER_API_KEY";

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
