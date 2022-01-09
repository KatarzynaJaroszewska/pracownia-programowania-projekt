package com.uam.pracowniaprogramowaniaprojekt.mapper;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Manufacturer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NewManufacturerMapper implements AbstractMapper<NewManufacturerDTO, Manufacturer> {
    @Override
    public NewManufacturerDTO mapToDto(Manufacturer entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, NewManufacturerDTO.class);
    }

    @Override
    public Manufacturer mapToEntity(NewManufacturerDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Manufacturer.class);
    }
}
