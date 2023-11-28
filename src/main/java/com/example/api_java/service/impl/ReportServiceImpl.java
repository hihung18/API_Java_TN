package com.example.api_java.service.impl;

import com.example.api_java.model.dto.ReportDTO;
import com.example.api_java.model.entity.Image;
import com.example.api_java.model.entity.Report;
import com.example.api_java.repository.IBusinessTripRepository;
import com.example.api_java.repository.IImageRepository;
import com.example.api_java.repository.IReportRepository;
import com.example.api_java.repository.IUserDetailRepository;
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
    private final IBusinessTripRepository businessTripRepository;
    private final IImageRepository imageRepository;
    private final IUserDetailRepository userDetailRepository;
    private final ModelMapper modelMapper;

    public ReportServiceImpl(IReportRepository reportRepository, IBusinessTripRepository businessTripRepository, IImageRepository imageRepository, IUserDetailRepository userDetailRepository, ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.businessTripRepository = businessTripRepository;
        this.imageRepository = imageRepository;
        this.userDetailRepository = userDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReportDTO> findAll() {
        List<Report> reports = reportRepository.findAll();
        setImage(reports);
        return createFromEntities(reports);
    }

    public List<ReportDTO> findAll(Long businessTripID) {
        List<Report> reports = reportRepository.findAllByBusinessTrip_BusinessTripId(businessTripID);
        setImage(reports);
        return createFromEntities(reports);
    }

    @Override
    public ReportDTO findById(Long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        reportOptional.get().setImages(imageRepository.findAllByReport_ReportId(id));
        return reportOptional.map(this::createFromE).orElse(null);
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
        report.setBusinessTrip(businessTripRepository.findById(dto.getBusinessTripID()).orElse(null));
        report.setUserDetail(userDetailRepository.findById(dto.getUserID()).orElse(null));
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
        if (entity.getBusinessTrip() != null) {
            reportDTO.setBusinessTripID(entity.getBusinessTrip().getBusinessTripId());
        }
        if (entity.getUserDetail() != null) {
            reportDTO.setUserID(entity.getUserDetail().getUserId());
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
