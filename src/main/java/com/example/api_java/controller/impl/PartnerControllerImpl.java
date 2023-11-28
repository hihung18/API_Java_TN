package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.PartnerDTO;
import com.example.api_java.service.impl.PartnerServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/partners"})
@Tag(
        name = "Partner"
)
public class PartnerControllerImpl implements IBaseController<PartnerDTO, Long, PartnerServiceImpl>
    , IGetController<PartnerDTO, Long, PartnerServiceImpl> {
    @Resource
    @Getter
    private PartnerServiceImpl service;
}
