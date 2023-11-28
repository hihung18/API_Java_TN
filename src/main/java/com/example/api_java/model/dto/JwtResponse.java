package com.example.api_java.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {

    private String token;


    @JsonIgnore
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private  String phoneNumber;
    private  String address;

    private List<String> roles;

    public JwtResponse(String token, Long id, String username, String email, String fullName, String phoneNumber, String address, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roles = roles;
    }
}
