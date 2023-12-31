package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.ImageDTO;
import com.example.api_java.model.dto.ReportDTO;
import com.example.api_java.service.impl.ReportServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/reports"})
@Tag(name = "Report")
public class ReportControllerImpl implements IBaseController<ReportDTO, Long, ReportServiceImpl> {
    @Resource
    @Getter
    private ReportServiceImpl service;
    @GetMapping("")
    public List<ReportDTO> getAll(@RequestParam(required = false) Long businessTripID,
                                  @RequestParam(required = false) Long taskID) {
        if (businessTripID != null)
            return getService().findAllByBusinessTripID(businessTripID);
        else if (taskID != null)
            return getService().findAllByTaskID(taskID);
        else
            return getService().findAll();
    }
}
