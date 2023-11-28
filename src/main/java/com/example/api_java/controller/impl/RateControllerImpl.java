package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.RateDTO;
import com.example.api_java.service.impl.RateServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/rates"})
@Tag(name = "Rate")
public class RateControllerImpl implements IBaseController<RateDTO, Long, RateServiceImpl>
    , IGetController<RateDTO, Long, RateServiceImpl> {
    @Resource
    @Getter
    private RateServiceImpl service;
}
