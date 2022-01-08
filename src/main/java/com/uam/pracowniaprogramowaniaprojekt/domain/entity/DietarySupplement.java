package com.uam.pracowniaprogramowaniaprojekt.domain.entity;

import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.ProductCategory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DIETARY_SUPPLEMENT")
public class DietarySupplement extends AbstractBaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Manufacturer manufacturer;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @ManyToMany
    @JoinTable(name = "supplement_ingredient",
                joinColumns = {
                    @JoinColumn(name = "dietary_supplement_id")
                })
    private Set<Ingredient> ingredients = new HashSet<>();

}
