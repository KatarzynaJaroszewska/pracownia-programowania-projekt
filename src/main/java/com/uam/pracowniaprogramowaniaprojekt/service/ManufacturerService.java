package com.uam.pracowniaprogramowaniaprojekt.service;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.ManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewManufacturerDTO;

import java.util.List;

public interface ManufacturerService {

    List<ManufacturerDTO> findAllManufacturers();

    ManufacturerDTO findById(Long id);

    ManufacturerDTO saveManufacturer(NewManufacturerDTO manufacturerDTO);

    ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO manufacturerDTO);

    ManufacturerDTO deleteManufacturer(Long id);

}
