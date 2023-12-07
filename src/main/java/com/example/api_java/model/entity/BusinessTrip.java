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
    @Column(name = "link_ggmap" , nullable = true)
    private String link_googleMap;
    @Column(name = "status" , nullable = true)
    private String statusBusinessTrip;


    @Column(name = "time_begin", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date time_begin_trip;

    @Column(name = "time_end", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date time_end_trip;

    @Column(name = "time_cre", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date time_cre_trip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_partner")
    private Partner partner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_manager")
    private UserDetail userDetail;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "businessTrip")
    private List<Report> reports;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "businessTrip")
    private List<Rate> rates;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "businessTrip")
    private List<Task> tasks;
}
