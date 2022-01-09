package com.uam.pracowniaprogramowaniaprojekt.dao;

import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
