package com.example.api_java.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class RateDTO {
    private Long rateId;
    private Long businessTripID;
    private Long userID;
    @NotNull
    private String commentRate;
    private Date time_cre_rate;
}
