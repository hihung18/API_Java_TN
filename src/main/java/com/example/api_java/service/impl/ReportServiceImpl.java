package com.example.api_java.service.impl;

import com.example.api_java.exception.NotFoundException;
import com.example.api_java.model.dto.ReportDTO;
import com.example.api_java.model.entity.Image;
import com.example.api_java.model.entity.Rate;
import com.example.api_java.model.entity.Report;
import com.example.api_java.repository.*;
import com.example.api_java.service.IBaseService;
import com.example.api_java.service.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements IBaseService<ReportDTO, Long>, IModelMapper<ReportDTO, Report> {
    private final IReportRepository reportRepository;
    private final ITaskRepository taskRepository;
    private final IImageRepository imageRepository;
    private final ModelMapper modelMapper;

    public ReportServiceImpl(IReportRepository reportRepository,  ITaskRepository taskRepository, IImageRepository imageRepository, ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.taskRepository = taskRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReportDTO> findAll() {
        List<Report> reports = reportRepository.findAll();
        setImage(reports);
        return createFromEntities(reports);
    }

    public List<ReportDTO> findAllByBusinessTripID(Long businessTripID) {
        List<Report> reports = reportRepository.findAllByBusinessId(businessTripID);
        setImage(reports);
        return createFromEntities(reports);
    }
    public List<ReportDTO> findAllByTaskID(Long taskID) {
        List<Report> reports = reportRepository.findAllByTask_TaskId(taskID);
        setImage(reports);
        return createFromEntities(reports);
    }

    @Override
    public ReportDTO findById(Long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        reportOptional.get().setImages(imageRepository.findAllByReport_ReportId(id));
        return reportOptional.map(this::createFromE)
                .orElseThrow(() -> new NotFoundException(Report.class, id));
    }

    @Override
    public ReportDTO save(ReportDTO reportDTO) {
        Report reportEntity = createFromD(reportDTO);
        Report savedReport = reportRepository.save(reportEntity);
        return createFromE(savedReport);
    }

    @Override
    public ReportDTO update(Long id, ReportDTO reportDTO) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        if (reportOptional.isPresent()) {
            Report reportEntity = reportOptional.get();
            Report updatedReport = updateEntity(reportEntity, reportDTO);
            Report savedReport = reportRepository.save(updatedReport);
            return createFromE(savedReport);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public ReportDTO delete(Long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        if (reportOptional.isPresent()) {
            Report reportEntity = reportOptional.get();
            reportRepository.delete(reportEntity);
            return createFromE(reportEntity);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public Report createFromD(ReportDTO dto) {
        Report report = modelMapper.map(dto, Report.class);
        report.setTask(taskRepository.findById(dto.getTaskID()).orElse(null));
        // Xử lý các đường dẫn hình ảnh và tạo danh sách các đối tượng Image
        List<Image> images = new ArrayList<>();
        if (dto.getImageUrls() != null) {
            for (int i = 0; i < dto.getImageUrls().size(); i++) {
                Image image = new Image();
                String url = dto.getImageUrls().get(i);
                image.setImageUrl(url);
                image.setReport(report);
                images.add(image);
            }
        }
        report.setImages(images);
        return report;
    }

    @Override
    public ReportDTO createFromE(Report entity) {
        ReportDTO reportDTO = modelMapper.map(entity, ReportDTO.class);
        if (entity.getTask() != null) {
            reportDTO.setTaskID(entity.getTask().getTaskId());
        }
        // Lấy danh sách các đường dẫn hình ảnh từ danh sách Images và set vào ReportDTO
        if (entity.getImages() != null)
            reportDTO.setImageUrls(entity.getImages().stream().map(Image::getImageUrl).collect(Collectors.toList()));
        return reportDTO;
    }

    @Override
    public Report updateEntity(Report entity, ReportDTO dto) {
        modelMapper.map(dto, entity);
        return entity;
    }
    private void setImage(final List<Report> reports) {
        for (Report report : reports) {
            report.setImages(imageRepository.findAllByReport_ReportId(report.getReportId()));
        }
    }
}
