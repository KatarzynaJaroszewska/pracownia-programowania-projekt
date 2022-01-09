package com.uam.pracowniaprogramowaniaprojekt.mapper;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewIngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Ingredient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NewIngredientMapper implements AbstractMapper<NewIngredientDTO, Ingredient> {
    @Override
    public NewIngredientDTO mapToDto(Ingredient entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, NewIngredientDTO.class);
    }

    @Override
    public Ingredient mapToEntity(NewIngredientDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Ingredient.class);
    }
}
