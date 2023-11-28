package com.example.api_java.service.impl;

import com.example.api_java.exception.NotFoundException;
import com.example.api_java.model.dto.BusinessTripDTO;
import com.example.api_java.model.entity.BusinessTrip;
import com.example.api_java.repository.IBusinessTripRepository;
import com.example.api_java.service.IBaseService;
import com.example.api_java.service.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class BusinessTripServiceImpl implements IBaseService<BusinessTripDTO, Long>, IModelMapper<BusinessTripDTO, BusinessTrip> {
    private final IBusinessTripRepository repository;
    private final ModelMapper modelMapper;

    public BusinessTripServiceImpl(IBusinessTripRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BusinessTripDTO> findAll() {
        List<BusinessTrip> trips = repository.findAll();
        return createFromEntities(trips);
    }

    @Override
    public BusinessTripDTO findById(Long id) {
        BusinessTrip entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(BusinessTrip.class, id));
        return createFromE(entity);
    }

    @Override
    public BusinessTripDTO save(BusinessTripDTO businessTripDTO) {
        BusinessTrip tripEntity = createFromD(businessTripDTO);
        BusinessTrip savedTrip = repository.save(tripEntity);
        return createFromE(savedTrip);
    }

    @Override
    public BusinessTripDTO update(Long id, BusinessTripDTO businessTripDTO) {
        Optional<BusinessTrip> entity = repository.findById(id);
        entity.orElseThrow(() -> new NotFoundException(BusinessTrip.class, id));
        return createFromE(repository.save(updateEntity(entity.get(), businessTripDTO)));
    }

    @Override
    public BusinessTripDTO delete(Long id) {
        Optional<BusinessTrip> tripOptional = repository.findById(id);
        if (tripOptional.isPresent()) {
            BusinessTrip tripEntity = tripOptional.get();
            repository.delete(tripEntity);
            return createFromE(tripEntity);
        }
        return null;
    }

    @Override
    public BusinessTrip createFromD(BusinessTripDTO dto) {
        return modelMapper.map(dto, BusinessTrip.class);
    }

    @Override
    public BusinessTripDTO createFromE(BusinessTrip entity) {
        return modelMapper.map(entity, BusinessTripDTO.class);
    }

    @Override
    public BusinessTrip updateEntity(BusinessTrip entity, BusinessTripDTO dto) {
        modelMapper.map(dto, entity);
        return entity;
    }
}
