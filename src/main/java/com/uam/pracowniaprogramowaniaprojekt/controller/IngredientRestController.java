package com.uam.pracowniaprogramowaniaprojekt.controller;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.IngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewIngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientRestController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<IngredientDTO>> findAllIngredients() {
        List<IngredientDTO> allIngredients = this.ingredientService.findAllIngredients();
        return ResponseEntity.ok()
                .body(allIngredients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> findIngredient(@PathVariable("id") Long id) {
        IngredientDTO ingredientDTO = this.ingredientService.findById(id);
        return ResponseEntity.ok()
                .body(ingredientDTO);
    }

    @PostMapping("/")
    public ResponseEntity<IngredientDTO> addIngredient(@RequestBody NewIngredientDTO newIngredient) {
        return new ResponseEntity<>(ingredientService.saveIngredient(newIngredient), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable("id") Long id, @RequestBody IngredientDTO updatedIngredient) {
        IngredientDTO ingredientDTO = this.ingredientService.updateIngredient(id, updatedIngredient);
        return new ResponseEntity<>(ingredientDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IngredientDTO> deleteIngredient(@PathVariable("id") Long id) {
        IngredientDTO ingredientDTO = this.ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(ingredientDTO, HttpStatus.OK);
    }
}
