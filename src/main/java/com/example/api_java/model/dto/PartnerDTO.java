package com.example.api_java.model.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartnerDTO {
    private Long partnerId;
    @NotBlank
    private String name_pn;
    private String email_pn;
    private String phoneNum_pn;
    private String address_pn;
    private Long status_pn;
    @JsonIgnore
    private List<Long> businessTripID;
}
