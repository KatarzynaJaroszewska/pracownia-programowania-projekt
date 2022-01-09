package com.uam.pracowniaprogramowaniaprojekt.controller;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.DietarySupplementDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewOrUpdatedDietarySupplementDTO;
import com.uam.pracowniaprogramowaniaprojekt.service.DietarySupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/supplement")
public class DietarySupplementRestController {

    private final DietarySupplementService dietarySupplementService;

    @Autowired
    public DietarySupplementRestController(DietarySupplementService dietarySupplementService) {
        this.dietarySupplementService = dietarySupplementService;
    }

    @GetMapping("/")
    public ResponseEntity<List<DietarySupplementDTO>> findAllIngredients() {
        List<DietarySupplementDTO> allDietarySupplements = this.dietarySupplementService.findAllDietarySupplements();
        return ResponseEntity.ok()
                .body(allDietarySupplements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietarySupplementDTO> findDietarySupplement(@PathVariable("id") Long id) {
        DietarySupplementDTO dietarySupplementDTO = this.dietarySupplementService.findById(id);
        return ResponseEntity.ok()
                .body(dietarySupplementDTO);
    }

    @PostMapping("/")
    public ResponseEntity<DietarySupplementDTO> addDietarySupplement(@RequestBody NewOrUpdatedDietarySupplementDTO newDietarySupplement) {
        return new ResponseEntity<>(dietarySupplementService.saveDietarySupplement(newDietarySupplement), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietarySupplementDTO> updateDietarySupplement(@PathVariable("id") Long id, @RequestBody NewOrUpdatedDietarySupplementDTO updatedDietarySupplement) {
        DietarySupplementDTO dietarySupplementDTO = this.dietarySupplementService.updateDietarySupplement(id, updatedDietarySupplement);
        return new ResponseEntity<>(dietarySupplementDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DietarySupplementDTO> deleteDietarySupplement(@PathVariable("id") Long id) {
        DietarySupplementDTO dietarySupplementDTO = this.dietarySupplementService.deleteDietarySupplement(id);
        return new ResponseEntity<>(dietarySupplementDTO, HttpStatus.OK);
    }
}
