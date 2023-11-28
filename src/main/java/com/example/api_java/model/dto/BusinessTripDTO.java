package com.example.api_java.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BusinessTripDTO {
    private Long businessTripId;
    private Long managerID;
    private Long partnerID;
    private String name_trip;
    private String detail_trip;
    private String location_trip;
    private Date time_begin_trip;
    private Date time_end_trip;

    @JsonIgnore
    private List<Long> reportIDs;
    @JsonIgnore
    private List<Long> taskIDs;
    @JsonIgnore
    private List<Long> rateIDs;



}
