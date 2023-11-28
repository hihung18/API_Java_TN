package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.TaskDTO;
import com.example.api_java.service.impl.TaskServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/tasks"})
@Tag(name = "Task")
public class TaskControllerImpl implements IBaseController<TaskDTO, Long, TaskServiceImpl>
    , IGetController<TaskDTO, Long, TaskServiceImpl> {
    @Resource
    @Getter
    private TaskServiceImpl service;
}
