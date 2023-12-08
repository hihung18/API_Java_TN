package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.RateDTO;
import com.example.api_java.model.dto.TaskDTO;
import com.example.api_java.service.impl.TaskServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/tasks"})
@Tag(name = "Task")
public class TaskControllerImpl implements IBaseController<TaskDTO, Long, TaskServiceImpl> {
    @Resource
    @Getter
    private TaskServiceImpl service;

    @GetMapping("")
    public List<TaskDTO> getAll(@RequestParam(required = false) Long userID,
                                @RequestParam(required = false) Long businessTripID) {
        if (userID != null)
            return getService().findAllByUserID(userID);
        if (businessTripID != null)
            return getService().findAllByBusinessID(businessTripID);
        return getService().findAll();
    }
    @PostMapping("/responseTokenDevice")
    public ResponseEntity<Map<String,String>> updateTokenForTask(@RequestBody TaskDTO taskDTO) {
        Map<String,String> tokenDevice = service.saveResponeTokenDevice(taskDTO);
        if (tokenDevice != null) {
            return ResponseEntity.ok(tokenDevice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
