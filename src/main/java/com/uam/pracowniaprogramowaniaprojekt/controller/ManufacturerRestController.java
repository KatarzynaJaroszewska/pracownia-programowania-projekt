package com.uam.pracowniaprogramowaniaprojekt.controller;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.ManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacturer")
public class ManufacturerRestController {

    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerRestController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ManufacturerDTO>> findAllManufacturers() {
        List<ManufacturerDTO> allManufacturers = this.manufacturerService.findAllManufacturers();
        return ResponseEntity.ok()
                .body(allManufacturers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> findManufacturer(@PathVariable("id") Long id) {
        ManufacturerDTO manufacturerDTO = this.manufacturerService.findById(id);
        return ResponseEntity.ok()
                .body(manufacturerDTO);
    }

    @PostMapping("/")
    public ResponseEntity<ManufacturerDTO> addManufacturer(@RequestBody NewManufacturerDTO newManufacturer) {
        return new ResponseEntity<>(manufacturerService.saveManufacturer(newManufacturer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(@PathVariable("id") Long id, @RequestBody ManufacturerDTO updatedManufacturer) {
        ManufacturerDTO manufacturerDTO = this.manufacturerService.updateManufacturer(id, updatedManufacturer);
        return new ResponseEntity<>(manufacturerDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> deleteManufacturer(@PathVariable("id") Long id) {
        ManufacturerDTO manufacturerDTO = this.manufacturerService.deleteManufacturer(id);
        return new ResponseEntity<>(manufacturerDTO, HttpStatus.OK);
    }
}
