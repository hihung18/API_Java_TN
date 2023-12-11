package com.example.api_java.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long rateId;

    @Column(name = "comment")
    private String commentRate;

    @Column(name = "time_cre", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date time_cre_rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_task")
    private Task task;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_business_trip")
//    private BusinessTrip businessTrip;
//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employee")
    private UserDetail userDetail;
}
