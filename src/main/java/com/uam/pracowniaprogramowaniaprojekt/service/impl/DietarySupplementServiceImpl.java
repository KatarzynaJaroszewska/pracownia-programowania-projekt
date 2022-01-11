package com.uam.pracowniaprogramowaniaprojekt.service.impl;

import com.uam.pracowniaprogramowaniaprojekt.dao.CustomizedDietarySupplementRepository;
import com.uam.pracowniaprogramowaniaprojekt.dao.DietarySupplementRepository;
import com.uam.pracowniaprogramowaniaprojekt.dao.IngredientRepository;
import com.uam.pracowniaprogramowaniaprojekt.dao.ManufacturerRepository;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.DietarySupplementDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.DietarySupplementSearchCriteriaDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.IngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewOrUpdatedDietarySupplementDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.DietarySupplement;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Ingredient;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Manufacturer;
import com.uam.pracowniaprogramowaniaprojekt.mapper.DietarySupplementMapper;
import com.uam.pracowniaprogramowaniaprojekt.mapper.IngredientMapper;
import com.uam.pracowniaprogramowaniaprojekt.mapper.NewOrUpdatedDietarySupplementMapper;
import com.uam.pracowniaprogramowaniaprojekt.service.DietarySupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DietarySupplementServiceImpl implements DietarySupplementService {

    private final DietarySupplementRepository dietarySupplementRepository;

    private final IngredientRepository ingredientRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final CustomizedDietarySupplementRepository customizedDietarySupplementRepository;

    private final DietarySupplementMapper mapper;

    private final NewOrUpdatedDietarySupplementMapper newOrUpdatedDietarySupplementMapper;

    private final IngredientMapper ingredientMapper;

    @Autowired
    public DietarySupplementServiceImpl(DietarySupplementRepository dietarySupplementRepository, IngredientRepository ingredientRepository, ManufacturerRepository manufacturerRepository, CustomizedDietarySupplementRepository customizedDietarySupplementRepository, DietarySupplementMapper mapper, NewOrUpdatedDietarySupplementMapper newOrUpdatedDietarySupplementMapper, IngredientMapper ingredientMapper) {
        this.dietarySupplementRepository = dietarySupplementRepository;
        this.ingredientRepository = ingredientRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.customizedDietarySupplementRepository = customizedDietarySupplementRepository;
        this.mapper = mapper;
        this.newOrUpdatedDietarySupplementMapper = newOrUpdatedDietarySupplementMapper;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public List<DietarySupplementDTO> findAllDietarySupplements() {
        return this.mapper.mapToDtos(this.dietarySupplementRepository.findAll());
    }

    @Override
    public DietarySupplementDTO findById(Long id) {
        Optional<DietarySupplement> entity = this.dietarySupplementRepository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("DietarySupplement entity with id " + id + " not found");
        }
        return this.mapper.mapToDto(entity.get());
    }

    @Override
    public DietarySupplementDTO saveDietarySupplement(NewOrUpdatedDietarySupplementDTO dietarySupplementDTO) {
        DietarySupplement entity = newOrUpdatedDietarySupplementMapper.mapToEntity(dietarySupplementDTO);
        setManufacturerToEntity(entity, dietarySupplementDTO);
        setIngredientsToEntity(entity, dietarySupplementDTO);
        return mapper.mapToDto(dietarySupplementRepository.save(entity));
    }

    @Override
    public DietarySupplementDTO updateDietarySupplement(Long id, NewOrUpdatedDietarySupplementDTO dietarySupplementDTO) {
        Optional<DietarySupplement> optionalEntity = dietarySupplementRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Ingredient entity with id " + id + " not found");
        }
        DietarySupplement entityToUpdate = updateEntity(optionalEntity.get(), dietarySupplementDTO);
        return mapper.mapToDto(this.dietarySupplementRepository.save(entityToUpdate));
    }

    private DietarySupplement updateEntity(DietarySupplement entity, NewOrUpdatedDietarySupplementDTO dto) {
        setManufacturerToEntity(entity, dto);
        setIngredientsToEntity(entity, dto);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDosage(dto.getDosage());
        entity.setProductCategory(dto.getProductCategory());
        return entity;
    }

    private void setManufacturerToEntity(DietarySupplement entity, NewOrUpdatedDietarySupplementDTO dto) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(dto.getManufacturerId());
        if (manufacturer.isEmpty()) {
            throw new EntityNotFoundException("Manufacturer entity with id " + dto.getManufacturerId() + " not found");
        }
        entity.setManufacturer(manufacturer.get());
    }

    private void setIngredientsToEntity(DietarySupplement entity, NewOrUpdatedDietarySupplementDTO dietarySupplementDTO) {
        HashSet<Ingredient> ingredients = new HashSet<>();
        for (IngredientDTO ingredient : dietarySupplementDTO.getIngredients()) {
            if (ingredient.getId() != null) {
                Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredient.getId());
                if (optionalIngredient.isEmpty()) {
                    throw new EntityNotFoundException("Ingredient entity with id " + ingredient.getId() + " not found");
                }
                ingredients.add(optionalIngredient.get());
            } else {
                ingredients.add(ingredientMapper.mapToEntity(ingredient));
            }
        }
        entity.setIngredients(ingredients);
    }

    @Override
    public DietarySupplementDTO deleteDietarySupplement(Long id) {
        Optional<DietarySupplement> optionalEntity = this.dietarySupplementRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("DietarySupplement entity with id " + id + " not found");
        }
        this.dietarySupplementRepository.deleteById(id);
        return this.mapper.mapToDto(optionalEntity.get());
    }

    @Override
    public List<DietarySupplementDTO> findDietarySupplementsByCriteria(DietarySupplementSearchCriteriaDTO criteria) {
        return this.mapper.mapToDtos(customizedDietarySupplementRepository.findDietarySupplementsBySearchCriteria(criteria));
    }
}
