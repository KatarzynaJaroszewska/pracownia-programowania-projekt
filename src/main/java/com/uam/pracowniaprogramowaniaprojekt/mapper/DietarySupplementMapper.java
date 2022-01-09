package com.uam.pracowniaprogramowaniaprojekt.mapper;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.DietarySupplementDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.DietarySupplement;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DietarySupplementMapper implements AbstractMapper<DietarySupplementDTO, DietarySupplement> {
    @Override
    public DietarySupplementDTO mapToDto(DietarySupplement entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, DietarySupplementDTO.class);
    }

    @Override
    public DietarySupplement mapToEntity(DietarySupplementDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, DietarySupplement.class);
    }
}
