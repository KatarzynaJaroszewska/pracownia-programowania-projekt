package com.uam.pracowniaprogramowaniaprojekt.domain.embedded;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class SupplementIngredientId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long dietarySupplementId;

    private Long ingredientId;

    public SupplementIngredientId() {
    }

    public SupplementIngredientId(Long dietarySupplementId, Long ingredientId) {
        super();
        this.dietarySupplementId = dietarySupplementId;
        this.ingredientId = ingredientId;
    }

}
