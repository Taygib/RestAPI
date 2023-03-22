package tests;

import model.lombok.LoginBodyModelLombok;
import model.lombok.LoginResponseModelLombok;

import model.pojo.LoginResponseModelPojo;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ReqresTestWithLombok {

    @Test
    void responseWithCode() {
        get("https://reqres.in")
                .then()
                .statusCode(200);
    }

    @Test
    void responseWithCodeAndBody() {
        given()
                .log().all()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(12))
                .body("total_pages", is(2));
    }

    @Test
    void responseWithCode404() {
        given()
                .log().uri()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void responsePostCreate() {
        String data = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);
    }

    @Test
    void responsePostRegister() {
        LoginBodyModelLombok loginBody = new LoginBodyModelLombok();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("pistol");

        LoginResponseModelLombok loginResponse = given()
                .log().uri()
                .contentType(JSON)
                .body(loginBody)
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .extract().as(LoginResponseModelLombok.class);

        // assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
        assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

    @Test
    void responsePutUpdate() {
        String data = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    void responseDelete() {
        given()
                .log().uri()
                .contentType(JSON)
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

    @Test
    void responseGetList() {
        given()
                .log().uri()
                .contentType(JSON)
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.name", hasItem("true red"))
                .body("data.name", hasItem("cerulean"))
                .body("data.name", hasItem("fuchsia rose"))
                .body("data.name", hasItem("aqua sky"))
                .body("data.name", hasItem("tigerlily"))
                .body("data.name", hasItem("blue turquoise"));
    }
}
