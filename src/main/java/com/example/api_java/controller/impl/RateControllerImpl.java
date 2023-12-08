package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.ImageDTO;
import com.example.api_java.model.dto.RateDTO;
import com.example.api_java.service.impl.RateServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/rates"})
@Tag(name = "Rate")
public class RateControllerImpl implements IBaseController<RateDTO, Long, RateServiceImpl> {
    @Resource
    @Getter
    private RateServiceImpl service;

    @GetMapping("")
    public List<RateDTO> getAll(@RequestParam(required = false) Long userID,
                                @RequestParam(required = false) Long businessTripID) {
        if (userID != null)
            return getService().findAllByUserID(userID);
        if (businessTripID != null)
            return getService().findAllByBusinessID(businessTripID);
        return getService().findAll();
    }
    @PostMapping("/listTokenDevice")
    public ResponseEntity<Map<String, List<String>>> getListTokenDevice(@RequestBody RateDTO rateDTO) {
        Map<String, List<String>> tokenDevice = service.saveReturnListTokenDevice(rateDTO);
        if (tokenDevice != null) {
            return ResponseEntity.ok(tokenDevice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
