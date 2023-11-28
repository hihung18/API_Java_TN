package com.example.api_java.controller.impl;


import com.example.api_java.controller.IBaseController;
import com.example.api_java.controller.IGetController;
import com.example.api_java.model.dto.ImageDTO;
import com.example.api_java.service.impl.ImageServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping({"api/images"})
@Tag(
        name = "Image"
)
public class ImageControllerImpl implements IBaseController<ImageDTO, Long, ImageServiceImpl> {
    @Resource
    @Getter
    private ImageServiceImpl service;
    @GetMapping("")
    public List<ImageDTO> getAll(@RequestParam(required = false) Long reportId) {
        if (reportId != null)
            return getService().findAll(reportId);
        else
            return getService().findAll();
    }
}
