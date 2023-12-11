package com.example.api_java.service.impl;

import com.example.api_java.exception.NotFoundException;
import com.example.api_java.model.dto.RateDTO;
import com.example.api_java.model.entity.BusinessTrip;
import com.example.api_java.model.entity.Rate;
import com.example.api_java.model.entity.Task;
import com.example.api_java.model.entity.UserDetail;
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
    private final IUserDetailRepository userDetailRepository;
    private final ITaskRepository taskRepository;

    private final ModelMapper modelMapper;

    public RateServiceImpl(IRateRepository rateRepository,  IUserDetailRepository userDetailRepository, ITaskRepository taskRepository, ModelMapper modelMapper) {
        this.rateRepository = rateRepository;
        this.userDetailRepository = userDetailRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RateDTO> findAll() {
        List<Rate> rates = rateRepository.findAll();
        return createFromEntities(rates);
    }
    public List<RateDTO> findAllByTaskID(Long taskID) {
        List<Rate> rates = rateRepository.findAllByTask_TaskId(taskID);
        return createFromEntities(rates);
    }
    public List<RateDTO> findAllByBusinessTripID(Long businessTripID) {
        List<Rate> rates = rateRepository.findAllByBusinessID(businessTripID);
        return createFromEntities(rates);
    }

    @Override
    public RateDTO findById(Long id) {
        Optional<Rate> rateOptional = rateRepository.findById(id);
        return rateOptional.map(this::createFromE)
                .orElseThrow(() -> new NotFoundException(Rate.class, id));
    }

    @Override
    public RateDTO save(RateDTO rateDTO) {
        Rate rateEntity = createFromD(rateDTO);
        Rate savedRate = rateRepository.save(rateEntity);
        return createFromE(savedRate);
    }
    public Map<String, String> saveReturnListTokenDevice(RateDTO rateDTO) {
        Rate rateEntity = createFromD(rateDTO);
        Rate savedRate = rateRepository.save(rateEntity);
        RateDTO rateRP = createFromE(savedRate);
        Optional<Task> taskOptional = taskRepository.findById(rateRP.getTaskID());
        Optional<UserDetail> userDetailOptional = userDetailRepository.findById(taskOptional.get().getUserDetail().getUserId());
        Map<String, String> response = new HashMap<>();
        response.put("tokendevice",userDetailOptional.get().getTokeDevice());
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
        rate.setTask(taskRepository.findById(dto.getTaskID()).orElse(null));
        rate.setUserDetail(userDetailRepository.findById(dto.getUserID()).orElse(null));
        return rate;
    }

    @Override
    public RateDTO createFromE(Rate entity) {
        RateDTO rateDTO = modelMapper.map(entity, RateDTO.class);
        if (entity.getTask() != null) {
            rateDTO.setTaskID(entity.getTask().getTaskId());
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
