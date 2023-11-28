package com.example.api_java.controller.impl;

import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.BusinessTripDTO;
import com.example.api_java.service.impl.BusinessTripServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/businessTrips"})
@Tag(name = "BusinessTrip")
public class BusinessTripControllerImpl implements IBaseController<BusinessTripDTO, Long, BusinessTripServiceImpl>
    , IGetController<BusinessTripDTO, Long, BusinessTripServiceImpl> {
    @Resource
    @Getter
    private BusinessTripServiceImpl service;
}
