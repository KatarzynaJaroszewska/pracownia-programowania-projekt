package com.uam.pracowniaprogramowaniaprojekt.mapper;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.ManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Manufacturer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerMapper implements AbstractMapper<ManufacturerDTO, Manufacturer> {
    @Override
    public ManufacturerDTO mapToDto(Manufacturer entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, ManufacturerDTO.class);
    }

    @Override
    public Manufacturer mapToEntity(ManufacturerDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Manufacturer.class);
    }
}
