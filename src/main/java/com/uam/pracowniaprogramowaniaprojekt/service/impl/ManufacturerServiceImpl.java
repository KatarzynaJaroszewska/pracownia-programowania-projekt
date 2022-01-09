package com.uam.pracowniaprogramowaniaprojekt.service.impl;

import com.uam.pracowniaprogramowaniaprojekt.dao.ManufacturerRepository;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.ManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Manufacturer;
import com.uam.pracowniaprogramowaniaprojekt.mapper.ManufacturerMapper;
import com.uam.pracowniaprogramowaniaprojekt.mapper.NewManufacturerMapper;
import com.uam.pracowniaprogramowaniaprojekt.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository repository;

    private final ManufacturerMapper mapper;

    private final NewManufacturerMapper newManufacturerMapper;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository repository, ManufacturerMapper mapper, NewManufacturerMapper newManufacturerMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.newManufacturerMapper = newManufacturerMapper;
    }

    @Override
    public List<ManufacturerDTO> findAllManufacturers() {
        return this.mapper.mapToDtos(this.repository.findAll());
    }

    @Override
    public ManufacturerDTO findById(Long id) {
        Optional<Manufacturer> entity = this.repository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Manufacturer entity with id " + id + " not found");
        }
        return this.mapper.mapToDto(entity.get());
    }

    @Override
    public ManufacturerDTO saveManufacturer(NewManufacturerDTO manufacturerDTO) {
        Manufacturer entity = this.repository.save(newManufacturerMapper.mapToEntity(manufacturerDTO));
        return mapper.mapToDto(entity);
    }

    @Override
    public ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO manufacturerDTO) {
        Optional<Manufacturer> optionalEntity = this.repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Manufacturer entity with id " + id + " not found");
        }
        Manufacturer entityToUpdate = updateEntity(optionalEntity.get(), manufacturerDTO);

        return mapper.mapToDto(this.repository.save(entityToUpdate));
    }

    private Manufacturer updateEntity(Manufacturer entity, ManufacturerDTO dto) {
        entity.setAddress(dto.getAddress());
        entity.setName(dto.getName());
        entity.setContactDetails(dto.getContactDetails());
        return entity;
    }

    @Override
    public ManufacturerDTO deleteManufacturer(Long id) {
        Optional<Manufacturer> optionalEntity = this.repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Manufacturer entity with id " + id + " not found");
        }
        this.repository.deleteById(id);
        return this.mapper.mapToDto(optionalEntity.get());
    }
}
