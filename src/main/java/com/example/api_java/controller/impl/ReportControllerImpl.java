package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.ReportDTO;
import com.example.api_java.service.impl.ReportServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/reports"})
@Tag(name = "Report")
public class ReportControllerImpl implements IBaseController<ReportDTO, Long, ReportServiceImpl>
    , IGetController<ReportDTO, Long, ReportServiceImpl> {
    @Resource
    @Getter
    private ReportServiceImpl service;
}
