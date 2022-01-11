package com.uam.pracowniaprogramowaniaprojekt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietarySupplementSearchCriteriaDTO {

    private String name;

    private String manufacturerName;

    private String productCategory;

    private String ingredientName;

}
