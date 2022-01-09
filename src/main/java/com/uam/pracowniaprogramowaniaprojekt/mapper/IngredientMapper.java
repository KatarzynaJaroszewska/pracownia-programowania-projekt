package com.uam.pracowniaprogramowaniaprojekt.mapper;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.IngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Ingredient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper implements AbstractMapper<IngredientDTO, Ingredient> {
    @Override
    public IngredientDTO mapToDto(Ingredient entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, IngredientDTO.class);
    }

    @Override
    public Ingredient mapToEntity(IngredientDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Ingredient.class);
    }
}
