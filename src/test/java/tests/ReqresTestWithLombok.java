package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import model.lombok.postCreate.PostCreateJob;
import model.lombok.postCreate.ResponsePostCreateModel;
import model.lombok.postRegister.LoginBodyModelLombok;
import model.lombok.postRegister.LoginResponseModelLombok;

import model.lombok.putUpdate.PutUpdateModel;
import model.lombok.putUpdate.ResponsePutUpdateModel;

import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ReqresTestWithLombok {

    @Test
    void responseWithCode() {
        step("Проверка статус кода 200", () -> {
            get("https://reqres.in")
                    .then()
                    .statusCode(200);
        });
    }

    @Test
    void responseWithCodeAndBody() {
        step("Проверка значении total и total_pages", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .log().all()
                    .get("https://reqres.in/api/users?page=2")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("total", is(12))
                    .body("total_pages", is(2));
        });
    }

    @Test
    void responseWithCode404() {
        step("Проверка статус кода 404", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .log().uri()
                    .get("https://reqres.in/api/users/23")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(404);
        });
    }

    @Test
    void responsePostCreate() {
        ResponsePostCreateModel PostCreate = new ResponsePostCreateModel();
        PostCreate.setName("morpheus");
        PostCreate.setJob("leader");

        PostCreateJob CreateJob =
                step("Добавление нового работника  ", () ->
                        given()
                                .filter(new AllureRestAssured())
                                .log().uri()
                                .contentType(JSON)
                                .body(PostCreate)
                                .post("https://reqres.in/api/users")
                                .then()
                                .log().status()
                                .log().body()
                                .statusCode(201)
                                .extract().as(PostCreateJob.class));

        step("Проверка нового работника с именем morpheus и должностю leader", () -> {
            assertThat(CreateJob.getJob()).isEqualTo("leader");
            assertThat(CreateJob.getName()).isEqualTo("morpheus");
        });
    }

    @Test
    void responsePostRegister() {
        LoginBodyModelLombok loginBody = new LoginBodyModelLombok();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("pistol");

        LoginResponseModelLombok loginResponse =
                step("Регистрация ввод логина и пароля", () ->
                        given()
                                .filter(new AllureRestAssured())
                                .log().uri()
                                .contentType(JSON)
                                .body(loginBody)
                                .post("https://reqres.in/api/register")
                                .then()
                                .log().status()
                                .log().body()
                                .statusCode(200)
                                .body("id", is(4))
                                .extract().as(LoginResponseModelLombok.class));

        step("Проверка токена после регистрации", () -> {
            assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        });
    }

    @Test
    void responsePutUpdate() {
        ResponsePutUpdateModel PutUpdate = new ResponsePutUpdateModel();
        PutUpdate.setName("morpheus");
        PutUpdate.setJob("zion resident");

        PutUpdateModel Update =
                step("Изменение назввание должности работника на zion resident", () ->
                        given()
                                .filter(new AllureRestAssured())
                                .log().uri()
                                .contentType(JSON)
                                .body(PutUpdate)
                                .put("https://reqres.in/api/users/2")
                                .then()
                                .log().status()
                                .log().body()
                                .statusCode(200)
                                .extract().as(PutUpdateModel.class));

        step("Проверка нового названия должности zion resident", () -> {
            assertThat(Update.getName()).isEqualTo("morpheus");
            assertThat(Update.getJob()).isEqualTo("zion resident");
        });
    }

    @Test
    void responseDelete() {
        step("Удаление", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .log().uri()
                    .contentType(JSON)
                    .delete("https://reqres.in/api/users/2")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(204);
        });
    }

    @Test
    void responseGetList() {
        step("Проверка названия цветов", () -> {
            given()
                    .filter(new AllureRestAssured())
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
        });
    }
}
