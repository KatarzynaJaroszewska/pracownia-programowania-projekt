package com.uam.pracowniaprogramowaniaprojekt.service;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.DietarySupplementDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewOrUpdatedDietarySupplementDTO;

import java.util.List;

public interface DietarySupplementService {

    List<DietarySupplementDTO> findAllDietarySupplements();

    DietarySupplementDTO findById(Long id);

    DietarySupplementDTO saveDietarySupplement(NewOrUpdatedDietarySupplementDTO dietarySupplementDTO);

    DietarySupplementDTO updateDietarySupplement(Long id, NewOrUpdatedDietarySupplementDTO dietarySupplementDTO);

    DietarySupplementDTO deleteDietarySupplement(Long id);

    // TODO: 1/9/2022 add search by criteria method 
    
}
