package com.uam.pracowniaprogramowaniaprojekt.domain.entity;

import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.ProductCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DIETARY_SUPPLEMENT")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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

    // TODO: 1/10/2022 change cascade type
    @ManyToMany(
            cascade = {CascadeType.ALL}
    )
    @JoinTable(name = "supplement_ingredient",
                joinColumns = @JoinColumn(name = "dietary_supplement_id"),
                inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new HashSet<>();

}
