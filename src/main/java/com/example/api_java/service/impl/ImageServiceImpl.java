package com.example.api_java.service.impl;


import com.example.api_java.model.dto.ImageDTO;
import com.example.api_java.model.entity.Image;
import com.example.api_java.repository.IImageRepository;
import com.example.api_java.repository.IReportRepository;
import com.example.api_java.service.IBaseService;
import com.example.api_java.service.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements IBaseService<ImageDTO, Long>, IModelMapper<ImageDTO, Image> {
    private final IImageRepository imageRepository;
    private final IReportRepository reportRepository;
    private final ModelMapper modelMapper;

    public ImageServiceImpl(IImageRepository imageRepository, IReportRepository reportRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ImageDTO> findAll() {
        List<Image> images = imageRepository.findAll();
        return createFromEntities(images);
    }

    @Override
    public ImageDTO findById(Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        return imageOptional.map(this::createFromE).orElse(null);
    }

    @Override
    public ImageDTO save(ImageDTO imageDTO) {
        Image imageEntity = createFromD(imageDTO);
        Image savedImage = imageRepository.save(imageEntity);
        return createFromE(savedImage);
    }

    @Override
    public ImageDTO update(Long id, ImageDTO imageDTO) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            Image imageEntity = imageOptional.get();
            Image updatedImage = updateEntity(imageEntity, imageDTO);
            Image savedImage = imageRepository.save(updatedImage);
            return createFromE(savedImage);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public ImageDTO delete(Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            Image imageEntity = imageOptional.get();
            imageRepository.delete(imageEntity);
            return createFromE(imageEntity);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public Image createFromD(ImageDTO dto) {
        Image image = modelMapper.map(dto, Image.class);
        image.setReport(reportRepository.findById(dto.getReportID()).orElse(null));
        return image;
    }

    @Override
    public ImageDTO createFromE(Image entity) {
        ImageDTO imageDTO = modelMapper.map(entity, ImageDTO.class);
        if (entity.getReport() != null) {
            imageDTO.setReportID(entity.getReport().getReport_id());
        }
        return imageDTO;
    }

    @Override
    public Image updateEntity(Image entity, ImageDTO dto) {
        modelMapper.map(dto, entity);
        entity.setReport(reportRepository.findById(dto.getReportID()).orElse(null));
        return entity;
    }
}
