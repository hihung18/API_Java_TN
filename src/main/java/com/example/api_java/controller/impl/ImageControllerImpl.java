package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.ImageDTO;
import com.example.api_java.service.impl.ImageServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/images"})
@Tag(
        name = "Image"
)
public class ImageControllerImpl implements IBaseController<ImageDTO, Long, ImageServiceImpl>
    , IGetController<ImageDTO, Long, ImageServiceImpl> {
    @Resource
    @Getter
    private ImageServiceImpl service;
}
