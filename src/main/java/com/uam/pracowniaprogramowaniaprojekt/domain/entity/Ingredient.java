package com.uam.pracowniaprogramowaniaprojekt.domain.entity;

import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.IngredientCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "INGREDIENT")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Ingredient extends AbstractBaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private IngredientCategory ingredientCategory;

    @Column(nullable = false)
    private String amount;

}
