package com.uam.pracowniaprogramowaniaprojekt.domain.entity;

import com.uam.pracowniaprogramowaniaprojekt.domain.embedded.Address;
import com.uam.pracowniaprogramowaniaprojekt.domain.embedded.ContactDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MANUFACTURER")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Manufacturer extends AbstractBaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Embedded
    private Address address;

    @Column(nullable = false)
    private ContactDetails contactDetails;


}
