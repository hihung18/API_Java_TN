package com.example.api_java.service.impl;

import com.example.api_java.model.dto.TaskDTO;
import com.example.api_java.model.entity.Task;
import com.example.api_java.repository.IBusinessTripRepository;
import com.example.api_java.repository.ITaskRepository;
import com.example.api_java.repository.IUserDetailRepository;
import com.example.api_java.service.IBaseService;
import com.example.api_java.service.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements IBaseService<TaskDTO, Long>, IModelMapper<TaskDTO, Task> {
    private final ITaskRepository taskRepository;
    private final IBusinessTripRepository businessTripRepository;
    private final IUserDetailRepository userDetailRepository;

    private final ModelMapper modelMapper;

    public TaskServiceImpl(ITaskRepository taskRepository, IBusinessTripRepository businessTripRepository, IUserDetailRepository userDetailRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.businessTripRepository = businessTripRepository;
        this.userDetailRepository = userDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TaskDTO> findAll() {
        List<Task> tasks = taskRepository.findAll();
        return createFromEntities(tasks);
    }
    public List<TaskDTO> findAllByUserID(Long userID) {
        List<Task> tasks = taskRepository.findAllByUserDetail_UserId(userID);
        return createFromEntities(tasks);
    }
    public List<TaskDTO> findAllByBusinessID(Long businessId) {
        List<Task> tasks = taskRepository.findAllByBusinessTrip_BusinessTripId(businessId);
        return createFromEntities(tasks);
    }
    @Override
    public TaskDTO findById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        return taskOptional.map(this::createFromE).orElse(null);
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        Task taskEntity = createFromD(taskDTO);
        Task savedTask = taskRepository.save(taskEntity);
        return createFromE(savedTask);
    }

    @Override
    public TaskDTO update(Long id, TaskDTO taskDTO) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task taskEntity = taskOptional.get();
            Task updatedTask = updateEntity(taskEntity, taskDTO);
            Task savedTask = taskRepository.save(updatedTask);
            return createFromE(savedTask);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public TaskDTO delete(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task taskEntity = taskOptional.get();
            taskRepository.delete(taskEntity);
            return createFromE(taskEntity);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public Task createFromD(TaskDTO dto) {
        Task task = modelMapper.map(dto, Task.class);
        // Ánh xạ businessTrip và userDetail từ id trong TaskDTO
        task.setBusinessTrip(businessTripRepository.findById(dto.getBusinessTripID()).orElse(null));
        task.setUserDetail(userDetailRepository.findById(dto.getUserID()).orElse(null));
        return task;
    }

    @Override
    public TaskDTO createFromE(Task entity) {
        TaskDTO taskDTO = modelMapper.map(entity, TaskDTO.class);
        if (entity.getBusinessTrip() != null) {
            taskDTO.setBusinessTripID(entity.getBusinessTrip().getBusinessTripId());
        }
        if (entity.getUserDetail() != null) {
            taskDTO.setUserID(entity.getUserDetail().getUserId());
        }
        return taskDTO;
    }

    @Override
    public Task updateEntity(Task entity, TaskDTO dto) {
        modelMapper.map(dto, entity);
        return entity;
    }
}
