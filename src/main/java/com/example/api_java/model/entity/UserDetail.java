package com.example.api_java.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "userdetail")
@Getter
@Setter
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @NotNull(message = "Email name must not be null")
    @NotBlank(message = "Email name must not be blank")
    @Email(message = "Please input correct Email format")
    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;
    private String password;
    @Column(name = "token_device")
    private String tokeDevice;

    @ManyToOne(fetch = FetchType.LAZY)
    Role role;

    @Column(name = "fullname", nullable = true)
    private String fullName;

    @Column(name = "phone_num", nullable = true)
    private String phoneNumber;

    @Column(name = "address", nullable = true)
    private String address;



    public UserDetail(Long userId, String email, String username, String password, String fullName, String phoneNumber
            , String address,String tokenDevice, Role role) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.tokeDevice = tokenDevice;
        this.role = role;
    }

    public UserDetail() {

    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                '}';
    }
}
