package com.uam.pracowniaprogramowaniaprojekt.domain.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ContactDetails {

    @Column(nullable = false)
    private String website;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String email;

}
