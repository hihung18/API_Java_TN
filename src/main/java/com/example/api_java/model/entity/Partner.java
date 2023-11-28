package com.example.api_java.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "partner")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long partnerId;

    @Column(name = "name_pn")
    private String name_pn;

    @Column(name = "email", nullable = true)
    private String email_pn;

    @Column(name = "phone_num", nullable = true)
    private String phoneNum_pn;

    @Column(name = "address", nullable = true)
    private String address_pn;

    @Column(name = "status")
    private Long status_pn;

    @Transient
    @OneToMany(fetch = FetchType.LAZY)
    private List<BusinessTrip> businessTrips;
}
