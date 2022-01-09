package com.uam.pracowniaprogramowaniaprojekt.domain.dto;

import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.IngredientCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NewIngredientDTO {

    private String name;

    private IngredientCategory ingredientCategory;

    private String amount;

}
