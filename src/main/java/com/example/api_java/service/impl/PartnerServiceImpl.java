package com.example.api_java.service.impl;

import com.example.api_java.exception.NotFoundException;
import com.example.api_java.model.dto.PartnerDTO;
import com.example.api_java.model.entity.Partner;
import com.example.api_java.model.entity.Rate;
import com.example.api_java.repository.IPartnerRepository;
import com.example.api_java.service.IBaseService;
import com.example.api_java.service.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerServiceImpl implements IBaseService<PartnerDTO, Long>, IModelMapper<PartnerDTO, Partner> {
    private final IPartnerRepository partnerRepository;
    private final ModelMapper modelMapper;

    public PartnerServiceImpl(IPartnerRepository partnerRepository, ModelMapper modelMapper) {
        this.partnerRepository = partnerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PartnerDTO> findAll() {
        List<Partner> partners = partnerRepository.findAll();
        return createFromEntities(partners);
    }

    @Override
    public PartnerDTO findById(Long id) {
        Optional<Partner> partnerOptional = partnerRepository.findById(id);
        return partnerOptional.map(this::createFromE)
                .orElseThrow(() -> new NotFoundException(Partner.class, id));
    }

    @Override
    public PartnerDTO save(PartnerDTO partnerDTO) {
        Partner partnerEntity = createFromD(partnerDTO);
        Partner savedPartner = partnerRepository.save(partnerEntity);
        return createFromE(savedPartner);
    }

    @Override
    public PartnerDTO update(Long id, PartnerDTO partnerDTO) {
        Optional<Partner> partnerOptional = partnerRepository.findById(id);
        if (partnerOptional.isPresent()) {
            Partner partnerEntity = partnerOptional.get();
            Partner updatedPartner = updateEntity(partnerEntity, partnerDTO);
            Partner savedPartner = partnerRepository.save(updatedPartner);
            return createFromE(savedPartner);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public PartnerDTO delete(Long id) {
        Optional<Partner> partnerOptional = partnerRepository.findById(id);
        if (partnerOptional.isPresent()) {
            Partner partnerEntity = partnerOptional.get();
            partnerRepository.delete(partnerEntity);
            return createFromE(partnerEntity);
        }
        return null; // hoặc ném ra một exception cho trường hợp không tìm thấy
    }

    @Override
    public Partner createFromD(PartnerDTO dto) {
        return modelMapper.map(dto, Partner.class);
    }

    @Override
    public PartnerDTO createFromE(Partner entity) {
        return modelMapper.map(entity, PartnerDTO.class);
    }

    @Override
    public Partner updateEntity(Partner entity, PartnerDTO dto) {
        modelMapper.map(dto, entity);
        return entity;
    }
}
