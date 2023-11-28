package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.UserDetailDTO;
import com.example.api_java.service.impl.UserDetailServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/users"})
@Tag(name = "User")
public class UserControllerImpl implements IBaseController<UserDetailDTO, Long, UserDetailServiceImpl>
    , IGetController<UserDetailDTO, Long, UserDetailServiceImpl> {
    @Resource
    @Getter
    private UserDetailServiceImpl service;
}
