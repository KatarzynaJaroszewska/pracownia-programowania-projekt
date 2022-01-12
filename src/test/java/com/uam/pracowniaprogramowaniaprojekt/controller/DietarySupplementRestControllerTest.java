package com.uam.pracowniaprogramowaniaprojekt.controller;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.*;

import com.uam.pracowniaprogramowaniaprojekt.domain.entity.DietarySupplement;
import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.IngredientCategory;
import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.ProductCategory;
import com.uam.pracowniaprogramowaniaprojekt.mapper.DietarySupplementMapper;
import com.uam.pracowniaprogramowaniaprojekt.mapper.NewOrUpdatedDietarySupplementMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("hsql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DietarySupplementRestControllerTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Test
    public void shouldFindDietarySupplementsReturnAllRecordsAndStatus200() {
        int expectedNumberOfResults = 5;
        List<DietarySupplementDTO> resultList = RestAssured.get(uri + "/supplement/")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", DietarySupplementDTO.class);

        assertEquals(expectedNumberOfResults, resultList.size());
    }

    @Test
    void shouldFindDietarySupplementByIdReturnProperDietarySupplement() {
        Long id = 1L;
        ProductCategory expectedProductCategory = ProductCategory.FOR_KIDS;
        String expectedDietarySupplementName = "Witaminy dla dzieci";
        DietarySupplementDTO resultDTO = given()
                .pathParam("id", id)
                .when()
                .get(uri + "/supplement/{id}")
                .then()
                .statusCode(200)
                .extract().body().as(DietarySupplementDTO.class);

        assertEquals(expectedDietarySupplementName, resultDTO.getName());
        assertEquals(expectedProductCategory, resultDTO.getProductCategory());
        assertEquals(id, resultDTO.getId());
    }

    @Test
    void shouldFindDietarySupplementByIdReturnNotFoundWhenIdNotExist() {
        Long NON_EXISTING_ID = 100000L;
        given()
                .pathParam("id", NON_EXISTING_ID)
                .when()
                .get(uri + "/supplement/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldAddDietarySupplementCreateNewDietarySupplementAndReturnStatus201() {
        NewOrUpdatedDietarySupplementDTO newDietarySupplementDTO = createNewDietarySupplementForTest();

        DietarySupplementDTO createdDietarySupplement = given()
                .when()
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .body(newDietarySupplementDTO)
                .post(uri + "/supplement/")
                .then()
                .statusCode(201)
                .extract().body().as(DietarySupplementDTO.class);

        assertNotNull(createdDietarySupplement);
        assertNotNull(createdDietarySupplement.getId());
    }

    @Test
    void shouldUpdateDietarySupplementUpdateDietarySupplementProperlyAndReturnStatusCode200() {
        Long idOfUpdatedDietarySupplement = 1L;

        DietarySupplementDTO existingDietarySupplement = given()
                .pathParam("id", idOfUpdatedDietarySupplement)
                .when()
                .get(uri + "/supplement/{id}")
                .then()
                .statusCode(200)
                .extract().body().as(DietarySupplementDTO.class);

        String supplementNameBeforeUpdate = existingDietarySupplement.getName();
        existingDietarySupplement.setName("DietarySupplementNameAfterUpdate");
        NewOrUpdatedDietarySupplementDTO newOrUpdatedDietarySupplementDTO = createNewOrUpdatedDietarySupplementDTO(existingDietarySupplement);

        DietarySupplementDTO updatedDietarySupplement = given()
                .when()
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .pathParam("id", idOfUpdatedDietarySupplement)
                .body(newOrUpdatedDietarySupplementDTO)
                .put(uri + "/supplement/{id}")
                .then()
                .statusCode(200)
                .extract().body().as(DietarySupplementDTO.class);

        assertNotNull(existingDietarySupplement);
        assertNotEquals(supplementNameBeforeUpdate, updatedDietarySupplement.getName());
    }

    private NewOrUpdatedDietarySupplementDTO createNewOrUpdatedDietarySupplementDTO(DietarySupplementDTO existingDietarySupplement) {
        DietarySupplementMapper dietarySupplementMapper = new DietarySupplementMapper();
        NewOrUpdatedDietarySupplementMapper newOrUpdatedDietarySupplementMapper = new NewOrUpdatedDietarySupplementMapper();
        DietarySupplement entity = dietarySupplementMapper.mapToEntity(existingDietarySupplement);
        return newOrUpdatedDietarySupplementMapper.mapToDto(entity);
    }

    @Test
    void shouldDeleteDietarySupplementDeleteRecordProperlyAndReturnStatus200AtFirstDeleteCallAndStatus404WithSecondCall() {
        Long idOfRecordToBeDeleted = 5L;

        given()
                .pathParam("id", idOfRecordToBeDeleted)
                .delete(uri + "/supplement/{id}")
                .prettyPeek()
                .then()
                .statusCode(200);

        given()
                .pathParam("id", idOfRecordToBeDeleted)
                .delete(uri + "/supplement/{id}")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void shouldDeleteDietarySupplementCannotDeleteRecordWithRelationshipToOtherEntitiesAndReturn409() {
        Long idOfRecordWithRelationToOtherEntities = 1L;

        given()
                .pathParam("id", idOfRecordWithRelationToOtherEntities)
                .delete(uri + "/supplement/{id}")
                .prettyPeek()
                .then()
                .statusCode(409);
    }

    private NewOrUpdatedDietarySupplementDTO createNewDietarySupplementForTest() {
        NewOrUpdatedDietarySupplementDTO newDietarySupplementDTO = new NewOrUpdatedDietarySupplementDTO();

        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(1L);
        ingredientDTO.setName("Nowa witamina");
        ingredientDTO.setIngredientCategory(IngredientCategory.VITAMINS);
        ingredientDTO.setAmount("30 mg");
        Set<IngredientDTO> ingredients = new HashSet<>();
        ingredients.add(ingredientDTO);

        newDietarySupplementDTO.setName("New dietary supplement");
        newDietarySupplementDTO.setDescription("Sample description");
        newDietarySupplementDTO.setDosage("5 tabs per day");
        newDietarySupplementDTO.setIngredients(ingredients);
        newDietarySupplementDTO.setManufacturerId(1L);
        newDietarySupplementDTO.setProductCategory(ProductCategory.UNIVERSAL);

        return newDietarySupplementDTO;
    }

}