package com.uam.pracowniaprogramowaniaprojekt.domain.dto;

import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.ProductCategory;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DietarySupplementDTO {

    private Long id;

    private String name;

    private ManufacturerDTO manufacturer;

    private String description;

    private String dosage;

    private ProductCategory productCategory;

    private Set<IngredientDTO> ingredients = new HashSet<>();

}
