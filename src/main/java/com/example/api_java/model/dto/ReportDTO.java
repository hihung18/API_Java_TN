package com.example.api_java.model.dto;


import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ReportDTO {
    private Long reportId;
    private Long userID;
    private Long businessTripID;
    private String report_detail;
    private Date time_cre_rp;
    @NotBlank

    private List<String> imageUrls;
}
