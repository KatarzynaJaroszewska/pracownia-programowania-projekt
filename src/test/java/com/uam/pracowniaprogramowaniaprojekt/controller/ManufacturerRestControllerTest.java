package com.uam.pracowniaprogramowaniaprojekt.controller;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.ManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.embedded.Address;
import com.uam.pracowniaprogramowaniaprojekt.domain.embedded.ContactDetails;
import com.uam.pracowniaprogramowaniaprojekt.service.ManufacturerService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("hsql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ManufacturerRestControllerTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Test
    public void shouldFindManufacturersReturnAllRecordsAndStatus200() {
        int expectedNumberOfResults = 3;
        List<ManufacturerDTO> resultList = RestAssured.get(uri + "/manufacturer/")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", ManufacturerDTO.class);

        assertEquals(expectedNumberOfResults, resultList.size());
    }

    @Test
    void shouldFindManufacturerByIdReturnProperManufacturer() {
        Long id = 1L;
        String expectedManufacturerName = "Manufacturer 1";
        ManufacturerDTO resultDTO = given()
                .pathParam("id", id)
                .when()
                .get(uri + "/manufacturer/{id}")
                .then()
                .statusCode(200)
                .extract().body().as(ManufacturerDTO.class);

        assertEquals(expectedManufacturerName, resultDTO.getName());
        assertEquals(id, resultDTO.getId());
    }

    @Test
    void shouldFindManufacturerByIdReturnNotFoundWhenIdNotExist() {
        Long NON_EXISTING_ID = 100000L;
            given()
                .pathParam("id", NON_EXISTING_ID)
            .when()
                .get(uri + "/manufacturer/{id}")
            .then()
                .statusCode(404);
    }

    @Test
    void shouldAddManufacturerCreateNewManufacturerAndReturnStatus201() {
        NewManufacturerDTO newManufacturerDTO = createNewManufacturerForTest();

        ManufacturerDTO createdManufacturer = given()
                .when()
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .body(newManufacturerDTO)
                .post(uri + "/manufacturer/")
                .then()
                .statusCode(201)
                .extract().body().as(ManufacturerDTO.class);

        assertNotNull(createdManufacturer);
        assertNotNull(createdManufacturer.getId());
    }

    @Test
    void shouldUpdateManufacturerUpdateManufacturerProperlyAndReturnStatusCode200() {
        Long idOfUpdatedManufacturer = 1L;

        ManufacturerDTO existingManufacturer = given()
                .pathParam("id", idOfUpdatedManufacturer)
                .when()
                .get(uri + "/manufacturer/{id}")
                .then()
                .statusCode(200)
                .extract().body().as(ManufacturerDTO.class);

        String manufacturerNameBeforeUpdate = existingManufacturer.getName();
        existingManufacturer.setName("ManufacturerNameAfterUpdate");

        ManufacturerDTO updatedManufacturer = given()
                .when()
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .pathParam("id", idOfUpdatedManufacturer)
                .body(existingManufacturer)
                .put(uri + "/manufacturer/{id}")
                .then()
                .statusCode(200)
                .extract().body().as(ManufacturerDTO.class);

        assertNotNull(existingManufacturer);
        assertNotEquals(manufacturerNameBeforeUpdate, updatedManufacturer.getName());
    }

    @Test
    void shouldDeleteManufacturerDeleteRecordProperlyAndReturnStatus200AtFirstDeleteCallAndStatus404WithSecondCall() {
        Long idOfRecordToBeDeleted = 3L;

        given()
                .pathParam("id", idOfRecordToBeDeleted)
                .delete(uri + "/manufacturer/{id}")
                .prettyPeek()
                .then()
                .statusCode(200);

        given()
                .pathParam("id", idOfRecordToBeDeleted)
                .delete(uri + "/manufacturer/{id}")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void shouldDeleteManufacturerCannotDeleteRecordWithRelationshipToOtherEntitiesAndReturn409() {
        Long idOfRecordWithRelationToOtherEntities = 2L;

        given()
                .pathParam("id", idOfRecordWithRelationToOtherEntities)
                .delete(uri + "/manufacturer/{id}")
                .prettyPeek()
                .then()
                .statusCode(409);
    }

    private NewManufacturerDTO createNewManufacturerForTest() {
        NewManufacturerDTO newManufacturerDTO = new NewManufacturerDTO();
        Address address = new Address();
        address.setCity("Zakopane");
        address.setCountry("Polska");
        address.setHomeNumber("15");
        address.setStreet("ul. Grzybowa");
        address.setPostalCode("77-899");
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setEmail("newmanufacturer@com.pl");
        contactDetails.setPhoneNumber("000 000 000");
        contactDetails.setWebsite("mmm.com.pl");
        newManufacturerDTO.setName("New Manufacturer");
        newManufacturerDTO.setAddress(address);
        newManufacturerDTO.setContactDetails(contactDetails);
        return newManufacturerDTO;
    }
}