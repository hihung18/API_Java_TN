package com.example.api_java.controller.impl;

import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.BusinessTripDTO;
import com.example.api_java.model.dto.RateDTO;
import com.example.api_java.service.impl.BusinessTripServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/businessTrips"})
@Tag(name = "BusinessTrip")
public class BusinessTripControllerImpl implements IBaseController<BusinessTripDTO, Long, BusinessTripServiceImpl>{
    @Resource
    @Getter
    private BusinessTripServiceImpl service;
    @GetMapping("")
    public List<BusinessTripDTO> getAll(@RequestParam(required = false) Long managerID,
                                @RequestParam(required = false) Long userID,
                                @RequestParam(required = false) Long partnerID) {
        if (managerID != null)
            return getService().findAllByManagerID(managerID);
        if (userID != null)
            return getService().findAllByUserID(userID);
        if (partnerID != null)
            return getService().findAllByPartnerID(partnerID);
        return getService().findAll();
    }
}
