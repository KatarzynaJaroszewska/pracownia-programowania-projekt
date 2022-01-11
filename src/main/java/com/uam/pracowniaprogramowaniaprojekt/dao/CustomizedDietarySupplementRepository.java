package com.uam.pracowniaprogramowaniaprojekt.dao;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.DietarySupplementSearchCriteriaDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.DietarySupplement;

import java.util.List;

public interface CustomizedDietarySupplementRepository {

    List<DietarySupplement> findDietarySupplementsBySearchCriteria(DietarySupplementSearchCriteriaDTO criteria);

}
