package com.example.api_java.model.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TaskDTO {
    private Long taskId;
    private Long businessTripID;
    private Long userID;
    @NotBlank
    private String nameTask;
    private String detailTask;
    private Long statusConfirm;
    private Long statusComplete;
    private Date time_cre_task;
}
