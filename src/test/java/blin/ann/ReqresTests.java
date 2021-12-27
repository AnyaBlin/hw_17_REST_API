package blin.ann;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void successfullLogin() {
        String Data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .contentType(JSON)
                .body(Data)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
    @Test
    void unsuccessfullLogin() {
        String Data = "{ \"email\": \"peter@klaven\" }";
        given()
                .contentType(JSON)
                .body(Data)
                .when()
                .post("/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
    @Test
    void successfullReg() {
        String Data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        given()
                .contentType(JSON)
                .body(Data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"),"id",is(4));
    }
    @Test
    void unuccessfullReg() {
        String Data = "{ \"email\": \"sydney@fife\" }";
        given()
                .contentType(JSON)
                .body(Data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
    @Test
    void singleUserNotFound() {
        given()
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404)
                .body(is("{}"));
    }
}
