package com.uam.pracowniaprogramowaniaprojekt.controller;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.IngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewIngredientDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.IngredientCategory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("hsql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IngredientRestControllerTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Test
    public void shouldFindIngredientsReturnAllRecordsAndStatus200() {
        int expectedNumberOfResults = 6;
        List<IngredientDTO> resultList = RestAssured.get(uri + "/ingredient/")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", IngredientDTO.class);

        assertEquals(expectedNumberOfResults, resultList.size());
    }

    @Test
    void shouldFindIngredientByIdReturnProperIngredient() {
        Long id = 1L;
        String expectedIngredientName = "witamina c";
        IngredientDTO resultDTO = given()
                .pathParam("id", id)
                .when()
                .get(uri + "/ingredient/{id}")
                .then()
                .statusCode(200)
                .extract().body().as(IngredientDTO.class);

        assertEquals(expectedIngredientName, resultDTO.getName());
        assertEquals(id, resultDTO.getId());
    }

    @Test
    void shouldFindIngredientByIdReturnNotFoundWhenIdNotExist() {
        Long NON_EXISTING_ID = 100000L;
        given()
                .pathParam("id", NON_EXISTING_ID)
                .when()
                .get(uri + "/ingredient/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldAddIngredientCreateNewIngredientAndReturnStatus201() {
        NewIngredientDTO newIngredientDTO = createNewIngredientForTest();

        IngredientDTO createdIngredient = given()
                .when()
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .body(newIngredientDTO)
                .post(uri + "/ingredient/")
                .then()
                .statusCode(201)
                .extract().body().as(IngredientDTO.class);

        assertNotNull(createdIngredient);
        assertNotNull(createdIngredient.getId());
    }

    @Test
    void shouldUpdateIngredientUpdateIngredientProperlyAndReturnStatusCode200() {
        Long idOfUpdatedIngredient = 1L;

        IngredientDTO existingIngredient = given()
                .pathParam("id", idOfUpdatedIngredient)
                .when()
                .get(uri + "/ingredient/{id}")
                .then()
                .statusCode(200)
                .extract().body().as(IngredientDTO.class);

        String ingredientNameBeforeUpdate = existingIngredient.getName();
        existingIngredient.setName("IngredientNameAfterUpdate");

        IngredientDTO updatedIngredient = given()
                .when()
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .pathParam("id", idOfUpdatedIngredient)
                .body(existingIngredient)
                .put(uri + "/ingredient/{id}")
                .then()
                .statusCode(200)
                .extract().body().as(IngredientDTO.class);

        assertNotNull(existingIngredient);
        assertNotEquals(ingredientNameBeforeUpdate, updatedIngredient.getName());
    }

    @Test
    void shouldDeleteIngredientDeleteRecordProperlyAndReturnStatus200AtFirstDeleteCallAndStatus404WithSecondCall() {
        Long idOfRecordToBeDeleted = 6L;

        given()
                .pathParam("id", idOfRecordToBeDeleted)
                .delete(uri + "/ingredient/{id}")
                .prettyPeek()
                .then()
                .statusCode(200);

        given()
                .pathParam("id", idOfRecordToBeDeleted)
                .delete(uri + "/ingredient/{id}")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void shouldDeleteIngredientCannotDeleteRecordWithRelationshipToOtherEntitiesAndReturn409() {
        Long idOfRecordWithRelationToOtherEntities = 2L;

        given()
                .pathParam("id", idOfRecordWithRelationToOtherEntities)
                .delete(uri + "/ingredient/{id}")
                .prettyPeek()
                .then()
                .statusCode(409);
    }



    private NewIngredientDTO createNewIngredientForTest() {
        NewIngredientDTO newIngredientDTO = new NewIngredientDTO();
        newIngredientDTO.setName("Nowa witamina");
        newIngredientDTO.setIngredientCategory(IngredientCategory.VITAMINS);
        newIngredientDTO.setAmount("30 mg");
        return newIngredientDTO;
    }

}