package com.uam.pracowniaprogramowaniaprojekt.dao.impl;

import com.uam.pracowniaprogramowaniaprojekt.dao.CustomizedDietarySupplementRepository;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.DietarySupplementSearchCriteriaDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.DietarySupplement;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Ingredient;
import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.ProductCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomizedDietarySupplementRepositoryImpl implements CustomizedDietarySupplementRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<DietarySupplement> findDietarySupplementsBySearchCriteria(DietarySupplementSearchCriteriaDTO criteria) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(DietarySupplement.class);
        Metamodel metamodel = manager.getMetamodel();
        EntityType<DietarySupplement> dietarySupplementMetaModel = metamodel.entity(DietarySupplement.class);

        Root<DietarySupplement> root = criteriaQuery.from(DietarySupplement.class);
        Join<DietarySupplement, Ingredient> ingredientJoin = root.join(dietarySupplementMetaModel.getSet("ingredients", Ingredient.class));
        List<Predicate> predicates = prepareListOfPredicates(criteria, criteriaBuilder, root, ingredientJoin);
        criteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[]{}))
                .distinct(true);
        return manager.createQuery(criteriaQuery).getResultList();
    }

    private List<Predicate> prepareListOfPredicates(DietarySupplementSearchCriteriaDTO criteria, CriteriaBuilder criteriaBuilder, Root<DietarySupplement> root, Join<DietarySupplement, Ingredient> ingredientJoin) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), criteria.getName()+"%"));
        }
        if (criteria.getManufacturerName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("manufacturer").get("name"), criteria.getManufacturerName()));
        }
        if (criteria.getIngredientName() != null) {
            predicates.add(criteriaBuilder.like(ingredientJoin.get("name"), "%"+criteria.getIngredientName()+"%"));
        }
        if (criteria.getProductCategory() != null) {
            prepareProductCategoryPredicate(criteria, criteriaBuilder, root, predicates);
        }
        return predicates;
    }

    private void prepareProductCategoryPredicate(DietarySupplementSearchCriteriaDTO criteria, CriteriaBuilder criteriaBuilder, Root<DietarySupplement> root, List<Predicate> predicates) {
        ProductCategory searchedCategory = null;
        for (ProductCategory productCategory : ProductCategory.values()) {
            if (productCategory.toString().equals(criteria.getProductCategory())) {
                searchedCategory = productCategory;
            }
        }
        predicates.add(criteriaBuilder.equal(root.get("productCategory"), searchedCategory));
    }
}
