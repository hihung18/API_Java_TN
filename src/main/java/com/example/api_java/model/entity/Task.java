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
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long taskId;

    @Column(name = "name_task", nullable = true)
    private String nameTask;

    @Column(name = "task_detail")
    private String detailTask;
    @Column(name = "status_confirm")
    private Long statusConfirm;
    @Column(name = "status_checkin")
    private Long statusCheckIn;
    @Column(name = "status_complete")
    private Long statusComplete;

    @Column(name = "time_cre")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date time_cre_task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_business_trip")
    private BusinessTrip businessTrip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employee")
    private UserDetail userDetail;
}
