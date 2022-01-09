package com.uam.pracowniaprogramowaniaprojekt.mapper;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewOrUpdatedDietarySupplementDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.DietarySupplement;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class NewOrUpdatedDietarySupplementMapper implements AbstractMapper<NewOrUpdatedDietarySupplementDTO, DietarySupplement> {
    @Override
    public NewOrUpdatedDietarySupplementDTO mapToDto(DietarySupplement entity) {
        ModelMapper modelMapper = new ModelMapper();
        setSkippingFields(modelMapper);
        return modelMapper.map(entity, NewOrUpdatedDietarySupplementDTO.class);
    }

    @Override
    public DietarySupplement mapToEntity(NewOrUpdatedDietarySupplementDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        setSkippingFields(modelMapper);
        return modelMapper.map(dto, DietarySupplement.class);
    }

    private void setSkippingFields(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        PropertyMap<NewOrUpdatedDietarySupplementDTO, DietarySupplement> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getManufacturer());
            }
        };
        modelMapper.addMappings(propertyMap);
    }
}
