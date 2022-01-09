package com.uam.pracowniaprogramowaniaprojekt.dao;

import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
