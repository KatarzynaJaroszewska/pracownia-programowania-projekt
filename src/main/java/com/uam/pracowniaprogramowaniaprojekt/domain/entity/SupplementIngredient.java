package com.uam.pracowniaprogramowaniaprojekt.domain.entity;

import com.uam.pracowniaprogramowaniaprojekt.domain.embedded.SupplementIngredientId;

import javax.persistence.*;

@Entity
@Table(name = "SUPPLEMENT_INGREDIENT")
public class SupplementIngredient {

    @EmbeddedId
    private SupplementIngredientId id = new SupplementIngredientId();

    @ManyToOne
    @MapsId("dietarySupplementId")
    private DietarySupplement dietarySupplement;

    @ManyToOne
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @Column(nullable = false)
    private String amount;

}
