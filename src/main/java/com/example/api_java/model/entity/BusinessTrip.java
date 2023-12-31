package com.example.api_java.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "businesstrip")
public class BusinessTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long businessTripId;

    @Column(name = "name_trip")
    private String name_trip;

    @Column(name = "detail")
    private String detail_trip;

    @Column(name = "location" , nullable = true)
    private String location_trip;
    @Column(name = "latitude" , nullable = true)
    private Double latitudeTrip;
    @Column(name = "longitude" , nullable = true)
    private Double longitudeTrip;
    @Column(name = "status" , nullable = true)
    private String statusBusinessTrip;


    @Column(name = "time_begin", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date time_begin_trip;

    @Column(name = "time_end", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date time_end_trip;

    @Column(name = "time_cre", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date time_cre_trip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_partner")
    private Partner partner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_manager")
    private UserDetail userDetail;


    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "businessTrip")
    private List<Task> tasks;
}
