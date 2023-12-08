package com.example.api_java.service.impl;

import com.example.api_java.model.dto.RateDTO;
import com.example.api_java.model.entity.BusinessTrip;
import com.example.api_java.model.entity.Rate;
import com.example.api_java.model.entity.Task;
import com.example.api_java.model.entity.UserDetail;
import com.example.api_java.repository.IBusinessTripRepository;
import com.example.api_java.repository.IRateRepository;
import com.example.api_java.repository.ITaskRepository;
import com.example.api_java.repository.IUserDetailRepository;
import com.example.api_java.service.IBaseService;
import com.example.api_java.service.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RateServiceImpl implements IBaseService<RateDTO, Long>, IModelMapper<RateDTO, Rate> {
    private final IRateRepository rateRepository;
    private final IBusinessTripRepository businessTripRepository;
    private final IUserDetailRepository userDetailRepository;
    private final ITaskRepository taskRepository;

    private final ModelMapper modelMapper;

    public RateServiceImpl(IRateRepository rateRepository, IBusinessTripRepository businessTripRepository, IUserDetailRepository userDetailRepository, ITaskRepository taskRepository, ModelMapper modelMapper) {
        this.rateRepository = rateRepository;
        this.businessTripRepository = businessTripRepository;
        this.userDetailRepository = userDetailRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RateDTO> findAll() {
        List<Rate> rates = rateRepository.findAll();
        return createFromEntities(rates);
    }
    public List<RateDTO> findAllByUserID(Long userID) {
        List<Rate> rates = rateRepository.findAllByUserDetail_UserId(userID);
        return createFromEntities(rates);
    }
    public List<RateDTO> findAllByBusinessID(Long businessTripId) {
        List<Rate> rates = rateRepository.findAllByBusinessTrip_BusinessTripId(businessTripId);
        return createFromEntities(rates);
    }

    @Override
    public RateDTO findById(Long id) {
        Optional<Rate> rateOptional = rateRepository.findById(id);
        return rateOptional.map(this::createFromE).orElse(null);
    }

    @Override
    public RateDTO save(RateDTO rateDTO) {
        Rate rateEntity = createFromD(rateDTO);
        Rate savedRate = rateRepository.save(rateEntity);
        return createFromE(savedRate);
    }
    public Map<String, List<String>> saveReturnListTokenDevice(RateDTO rateDTO) {
        Rate rateEntity = createFromD(rateDTO);
        Rate savedRate = rateRepository.save(rateEntity);
        RateDTO rateRP = createFromE(savedRate);
        Optional<BusinessTrip> businessTripOptional = businessTripRepository.findById(rateRP.getBusinessTripID());
        List<Task> tasks = taskRepository.findAllByBusinessTrip_BusinessTripId(businessTripOptional.get().getBusinessTripId());
        Set<String> uniqueTokens = new LinkedHashSet<>();
        Map<String, List<String>> response = new HashMap<>();
        for (Task task : tasks ){
            Optional<UserDetail> userDetail = userDetailRepository.findById(task.getUserDetail().getUserId());
            uniqueTokens.add(userDetail.get().getTokeDevice());
        }
        List<String> listTokenDevice = new ArrayList<>(uniqueTokens);
        response.put("listTokenDevice",listTokenDevice);
        return response;
    }

    @Override
    public RateDTO update(Long id, RateDTO rateDTO) {
        Optional<Rate> rateOptional = rateRepository.findById(id);
        if (rateOptional.isPresent()) {
            Rate rateEntity = rateOptional.get();
            Rate updatedRate = updateEntity(rateEntity, rateDTO);
            Rate savedRate = rateRepository.save(updatedRate);
            return createFromE(savedRate);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public RateDTO delete(Long id) {
        Optional<Rate> rateOptional = rateRepository.findById(id);
        if (rateOptional.isPresent()) {
            Rate rateEntity = rateOptional.get();
            rateRepository.delete(rateEntity);
            return createFromE(rateEntity);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public Rate createFromD(RateDTO dto) {
        Rate rate = modelMapper.map(dto, Rate.class);
        rate.setBusinessTrip(businessTripRepository.findById(dto.getBusinessTripID()).orElse(null));
        rate.setUserDetail(userDetailRepository.findById(dto.getUserID()).orElse(null));
        return rate;
    }

    @Override
    public RateDTO createFromE(Rate entity) {
        RateDTO rateDTO = modelMapper.map(entity, RateDTO.class);
        if (entity.getBusinessTrip() != null) {
            rateDTO.setBusinessTripID(entity.getBusinessTrip().getBusinessTripId());
        }
        if (entity.getUserDetail() != null) {
            rateDTO.setUserID(entity.getUserDetail().getUserId());
        }
        return rateDTO;
    }

    @Override
    public Rate updateEntity(Rate entity, RateDTO dto) {
        modelMapper.map(dto, entity);
        return entity;
    }
}
