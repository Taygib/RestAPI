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
import static specs.SpecCode404.RequestSpecCode404;
import static specs.SpecCode404.ResponseSpecCode404;
import static specs.SpecCodeAndBody.*;
import static specs.SpecDelete.RequestSpecDelete;
import static specs.SpecDelete.ResponseSpecDelete;
import static specs.SpecGetList.RequestSpecGetList;
import static specs.SpecGetList.ResponseSpecGetList;
import static specs.SpecPostCreate.RequestSpecPostCreate;
import static specs.SpecPostCreate.ResponseSpecPostCreate;
import static specs.SpecPostRegister.RequestSpecPostRegister;
import static specs.SpecPostRegister.ResponseSpecPostRegister;
import static specs.SpecPutUpdate.RequestSpecPutUpdate;
import static specs.SpecPutUpdate.ResponseSpecPutUpdate;

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
            given(RequestSpecCodeAndBody)
                    .get("/users?page=2")
                    .then()
                    .spec(ResponseSpecCodeAndBody);
        });
    }

    @Test
    void responseWithCode404() {
        step("Проверка статус кода 404", () -> {
            given(RequestSpecCode404)
                    .filter(new AllureRestAssured())
                    .log().uri()
                    .get("/users/23")
                    .then()
                    .spec(ResponseSpecCode404);
        });
    }

    @Test
    void responsePostCreate() {
        ResponsePostCreateModel PostCreate = new ResponsePostCreateModel();
        PostCreate.setName("morpheus");
        PostCreate.setJob("leader");

        PostCreateJob CreateJob =
                step("Добавление нового работника  ", () ->
                        given(RequestSpecPostCreate)
                                .body(PostCreate)
                                .post("/users")
                                .then()
                                .spec(ResponseSpecPostCreate)
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
                        given(RequestSpecPostRegister)
                                .body(loginBody)
                                .post("/register")
                                .then()
                                .spec(ResponseSpecPostRegister)
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
                        given(RequestSpecPutUpdate)
                                .body(PutUpdate)
                                .put("/users/2")
                                .then()
                                .spec(ResponseSpecPutUpdate)
                                .extract().as(PutUpdateModel.class));

        step("Проверка нового названия должности zion resident", () -> {
            assertThat(Update.getName()).isEqualTo("morpheus");
            assertThat(Update.getJob()).isEqualTo("zion resident");
        });
    }

    @Test
    void responseDelete() {
        step("Удаление", () -> {
            given(RequestSpecDelete)
                    .filter(new AllureRestAssured())
                    .log().uri()
                    .contentType(JSON)
                    .delete("/users/2")
                    .then()
                    .spec(ResponseSpecDelete);
        });
    }

    @Test
    void responseGetList() {
        step("Проверка названия цветов", () -> {
            given(RequestSpecGetList)
                    .get("/unknown")
                    .then()
                    .spec(ResponseSpecGetList);
        });
    }
}