package com.example.api_java.model.dto;

import com.example.api_java.model.RoleName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequest {
    private Long id;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private RoleName roleName;
}
