package com.uam.pracowniaprogramowaniaprojekt.domain.dto;

import com.uam.pracowniaprogramowaniaprojekt.domain.embedded.Address;
import com.uam.pracowniaprogramowaniaprojekt.domain.embedded.ContactDetails;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ManufacturerDTO {

    private Long id;

    private String name;

    private Address address;

    private ContactDetails contactDetails;

}
