package com.example.api_java.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long report_id;

    @Column(name = "report_detail")
    private String report_detail;

    @Column(name = "time_cre", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date time_cre_rp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_business_trip")
    private BusinessTrip businessTrip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employee")
    private UserDetail userDetail;

    @Transient
    @OneToMany(fetch = FetchType.LAZY)
    private List<Image> images;
}
