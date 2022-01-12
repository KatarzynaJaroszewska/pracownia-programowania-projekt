package com.uam.pracowniaprogramowaniaprojekt.controller;

import com.uam.pracowniaprogramowaniaprojekt.domain.dto.IngredientDTO;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("hsql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImportExportDataRestControllerTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Test
    void shouldExportDatabaseReturnStatus200() {
        String result = given()
                .header("ContentType","application/json")
                .get(uri + "/data/export")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString(".");

        assertNotNull(result);
        assertThat(result.contains("[id:1, version:0, name:witamina c, ingredientCategory:VITAMINS, amount:100mg]"));
    }

    @Test
    void shouldImportDatabaseReturnStatus200() {

        String data = "[[{\"id\":100,\"version\":0,\"name\":\"ingredientFromImport\",\"ingredientCategory\":\"VITAMINS\",\"amount\":\"100mg\"},{\"id\":101,\"version\":0,\"name\":\"cascade test\",\"ingredientCategory\":\"VITAMINS\",\"amount\":\"100mg\"}],[{\"id\":100,\"version\":0,\"name\":\"Manufacturer from import\",\"address\":{\"street\":\"ul. Testowa\",\"homeNumber\":\"1\",\"postalCode\":\"61-666\",\"city\":\"Poznan\",\"country\":\"Polska\"},\"contactDetails\":{\"website\":\"manufacturer1.com\",\"phoneNumber\":\"111 111 111\",\"email\":\"manufacturer1@com.pl\"}}],[{\"id\":1,\"version\":0,\"name\":\"Example supplement from import\",\"manufacturer\":{\"id\":1,\"version\":0,\"name\":\"Manufacturer from import\",\"address\":{\"street\":\"ul. Testowa\",\"homeNumber\":\"1\",\"postalCode\":\"61-666\",\"city\":\"Poznan\",\"country\":\"Polska\"},\"contactDetails\":{\"website\":\"manufacturer1.com\",\"phoneNumber\":\"111 111 111\",\"email\":\"manufacturer1@com.pl\"}},\"description\":\"Example description\",\"dosage\":\"Jedna kapsulka dziennie\",\"productCategory\":\"FOR_ADULT\",\"ingredients\":[{\"id\":100,\"version\":0,\"name\":\"ingredientFromImport\",\"ingredientCategory\":\"VITAMINS\",\"amount\":\"100mg\"},{\"id\":101,\"version\":0,\"name\":\"cascade test\",\"ingredientCategory\":\"VITAMINS\",\"amount\":\"100mg\"}]}]]";

        given()
                .header("ContentType","application/json")
                .when()
                .body(data)
                .post(uri + "/data/import")
                .then()
                .statusCode(200);

    }
}