package com.uam.pracowniaprogramowaniaprojekt.service;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.IngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewIngredientDTO;

import java.util.List;

public interface IngredientService {

    List<IngredientDTO> findAllIngredients();

    IngredientDTO findById(Long id);

    IngredientDTO saveIngredient(NewIngredientDTO ingredientDTO);

    IngredientDTO updateIngredient(Long id, IngredientDTO ingredientDTO);

    IngredientDTO deleteIngredient(Long id);

}
