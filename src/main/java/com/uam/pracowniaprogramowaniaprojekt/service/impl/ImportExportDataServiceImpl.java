package com.uam.pracowniaprogramowaniaprojekt.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uam.pracowniaprogramowaniaprojekt.dao.DietarySupplementRepository;
import com.uam.pracowniaprogramowaniaprojekt.dao.IngredientRepository;
import com.uam.pracowniaprogramowaniaprojekt.dao.ManufacturerRepository;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.DietarySupplement;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Ingredient;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Manufacturer;
import com.uam.pracowniaprogramowaniaprojekt.service.ImportExportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ImportExportDataServiceImpl implements ImportExportDataService {

    private final IngredientRepository ingredientRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final DietarySupplementRepository dietarySupplementRepository;

    @Autowired
    public ImportExportDataServiceImpl(IngredientRepository ingredientRepository, ManufacturerRepository manufacturerRepository, DietarySupplementRepository dietarySupplementRepository) {
        this.ingredientRepository = ingredientRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.dietarySupplementRepository = dietarySupplementRepository;
    }

    public String exportData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Ingredient> ingredients = ingredientRepository.findAll();
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        List<DietarySupplement> dietarySupplements = dietarySupplementRepository.findAll();

        List<Object> exportList = new ArrayList<>();
        exportList.add(ingredients);
        exportList.add(manufacturers);
        exportList.add(dietarySupplements);

        return objectMapper.writeValueAsString(exportList);
    }

    @Override
    public void importData(String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> listOfSplitedString = splitJsonData(data);
//        I assume the order of import is constant
        String ingredientsFromImport = listOfSplitedString.get(0);
        String manufacturersFromImport = listOfSplitedString.get(1);
        String dietarySupplementsFromImport = listOfSplitedString.get(2);

        saveIngredientsFromImportToDatabase(objectMapper, ingredientsFromImport);
        saveManufacturersFromImportToDatabase(objectMapper, manufacturersFromImport);
        saveDietarySupplementsFromImportToDatabase(objectMapper, dietarySupplementsFromImport);
    }

    private void saveDietarySupplementsFromImportToDatabase(ObjectMapper objectMapper, String dietarySupplementsFromImport) throws JsonProcessingException {
        List<DietarySupplement> listOfDietarySupplement = objectMapper.readValue(dietarySupplementsFromImport, new TypeReference<List<DietarySupplement>>() {});
        for (DietarySupplement dietarySupplement : listOfDietarySupplement) {
            dietarySupplementRepository.save(dietarySupplement);
        }
    }

    private void saveManufacturersFromImportToDatabase(ObjectMapper objectMapper, String manufacturersFromImport) throws JsonProcessingException {
        List<Manufacturer> listOfManufacturers = objectMapper.readValue(manufacturersFromImport, new TypeReference<List<Manufacturer>>() {});
        for (Manufacturer manufacturer : listOfManufacturers) {
            manufacturerRepository.save(manufacturer);
        }
    }

    private void saveIngredientsFromImportToDatabase(ObjectMapper objectMapper, String ingredientsFromImport) throws JsonProcessingException {
        List<Ingredient> listOfIngredients = objectMapper.readValue(ingredientsFromImport, new TypeReference<List<Ingredient>>() {});
        for (Ingredient ingredient : listOfIngredients) {
            ingredientRepository.save(ingredient);
        }
    }

    private List<String> splitJsonData(String data) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = new ArrayList<>();
        int counter = 0;
        boolean isWriting = false;
        for (int i = 1; i < data.length(); i++) {
            if (data.charAt(i) == '[') {
                counter++;
                isWriting = true;
            }
            if (data.charAt(i) == ']') {
                counter--;
            }
            if (counter > 0) {
                stringBuilder.append(data.charAt(i));
            }
            if (counter == 0 && isWriting) {
                stringBuilder.append(data.charAt(i));
                String dataWithTheSameType = stringBuilder.toString();
                list.add(dataWithTheSameType);
                stringBuilder = new StringBuilder();
                isWriting = false;
            }
        }
        for (String string : list) {
            System.out.println("New element: " + string);
        }
        return list;
    }

}
