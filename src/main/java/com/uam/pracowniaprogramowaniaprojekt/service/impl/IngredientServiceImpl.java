package com.uam.pracowniaprogramowaniaprojekt.service.impl;

import com.uam.pracowniaprogramowaniaprojekt.dao.IngredientRepository;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.IngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewIngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Ingredient;
import com.uam.pracowniaprogramowaniaprojekt.mapper.IngredientMapper;
import com.uam.pracowniaprogramowaniaprojekt.mapper.NewIngredientMapper;
import com.uam.pracowniaprogramowaniaprojekt.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repository;

    private final IngredientMapper mapper;

    private final NewIngredientMapper newIngredientMapper;

    @Autowired
    public IngredientServiceImpl(IngredientRepository repository, IngredientMapper mapper, NewIngredientMapper newIngredientMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.newIngredientMapper = newIngredientMapper;
    }

    @Override
    public List<IngredientDTO> findAllIngredients() {
        return this.mapper.mapToDtos(this.repository.findAll());
    }

    @Override
    public IngredientDTO findById(Long id) {
        Optional<Ingredient> entity = this.repository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Ingredient entity with id " + id + " not found");
        }
        return this.mapper.mapToDto(entity.get());
    }

    @Override
    public IngredientDTO saveIngredient(NewIngredientDTO ingredientDTO) {
        Ingredient entity = this.repository.save(newIngredientMapper.mapToEntity(ingredientDTO));
        return mapper.mapToDto(entity);
    }

    @Override
    public IngredientDTO updateIngredient(Long id, IngredientDTO ingredientDTO) {
        Optional<Ingredient> optionalEntity = this.repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Ingredient entity with id " + id + " not found");
        }
        Ingredient entityToUpdate = updateEntity(optionalEntity.get(), ingredientDTO);

        return mapper.mapToDto(this.repository.save(entityToUpdate));
    }

    private Ingredient updateEntity(Ingredient entity, IngredientDTO dto) {
        entity.setName(dto.getName());
        entity.setIngredientCategory(dto.getIngredientCategory());
        entity.setAmount(dto.getAmount());
        return entity;
    }

    @Override
    public IngredientDTO deleteIngredient(Long id) {
        Optional<Ingredient> optionalEntity = this.repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Ingredient entity with id " + id + " not found");
        }
        this.repository.deleteById(id);
        return this.mapper.mapToDto(optionalEntity.get());
    }
}
