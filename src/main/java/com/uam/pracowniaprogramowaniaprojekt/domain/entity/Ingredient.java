package com.uam.pracowniaprogramowaniaprojekt.domain.entity;

import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.IngredientCategory;

import javax.persistence.*;

@Entity
@Table(name = "INGREDIENT")
public class Ingredient extends AbstractBaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private IngredientCategory ingredientCategory;

}
