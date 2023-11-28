package com.example.api_java.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.api_java.model.RoleName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDetailDTO {
    private Long userId;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 20, min = 3)
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private RoleName roleName;
}
