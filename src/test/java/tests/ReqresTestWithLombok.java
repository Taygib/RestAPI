package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import model.lombok.postcreate.PostCreateJob;
import model.lombok.postcreate.ResponsePostCreateModel;
import model.lombok.postregister.LoginBodyModelLombok;
import model.lombok.postregister.LoginResponseModelLombok;
import model.lombok.putupdate.PutUpdateModel;
import model.lombok.putupdate.ResponsePutUpdateModel;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.SpecCode404.*;
import static specs.SpecCodeAndBody.*;
import static specs.SpecDelete.*;
import static specs.SpecGetList.*;
import static specs.SpecPostCreate.*;
import static specs.SpecPostRegister.*;
import static specs.SpecPutUpdate.*;

public class ReqresTestWithLombok {

    @Test
    @Tag("APITest")
    void responseWithCode() {
        step("Проверка статус кода 200", () -> {
            get("https://reqres.in")
                    .then()
                    .statusCode(200);
        });
    }

    @Test
    @Tag("APITest")
    void responseWithCodeAndBody() {
        step("Проверка значении total и total_pages", () -> {
            given(requestSpecCodeAndBody)
                    .get("/users?page=2")
                    .then()
                    .spec(responseSpecCodeAndBody);
        });
    }

    @Test
    @Tag("APITest")
    void responseWithCode404() {
        step("Проверка статус кода 404", () -> {
            given(requestSpecCode404)
                    .get("/users/23")
                    .then()
                    .spec(responseSpecCode404);
        });
    }

    @Test
    @Tag("APITest")
    void responsePostCreate() {
        ResponsePostCreateModel postCreate = new ResponsePostCreateModel();
        postCreate.setName("morpheus");
        postCreate.setJob("leader");

        PostCreateJob createJob =
                step("Добавление нового работника  ", () ->
                        given(requestSpecPostCreate)
                                .body(postCreate)
                                .post("/users")
                                .then()
                                .spec(responseSpecPostCreate)
                                .extract().as(PostCreateJob.class));

        step("Проверка нового работника с именем morpheus и должностю leader", () -> {
            assertThat(createJob.getJob()).isEqualTo("leader");
            assertThat(createJob.getName()).isEqualTo("morpheus");
        });
    }

    @Test
    @Tag("APITest")
    void responsePostRegister() {
        LoginBodyModelLombok loginBody = new LoginBodyModelLombok();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("pistol");

        LoginResponseModelLombok loginResponse =
                step("Регистрация ввод логина и пароля", () ->
                        given(requestSpecPostRegister)
                                .body(loginBody)
                                .post("/register")
                                .then()
                                .spec(responseSpecPostRegister)
                                .extract().as(LoginResponseModelLombok.class));

        step("Проверка токена после регистрации", () -> {
            assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        });
    }

    @Test
    @Tag("APITest")
    void responsePutUpdate() {
        ResponsePutUpdateModel putUpdate = new ResponsePutUpdateModel();
        putUpdate.setName("morpheus");
        putUpdate.setJob("zion resident");

        PutUpdateModel update =
                step("Изменение назввание должности работника на zion resident", () ->
                        given(requestSpecPutUpdate)
                                .body(putUpdate)
                                .put("/users/2")
                                .then()
                                .spec(responseSpecPutUpdate)
                                .extract().as(PutUpdateModel.class));

        step("Проверка нового названия должности zion resident", () -> {
            assertThat(update.getName()).isEqualTo("morpheus");
            assertThat(update.getJob()).isEqualTo("zion resident");
        });
    }

    @Test
    @Tag("APITest")
    void responseDelete() {
        step("Удаление", () -> {
            given(requestSpecDelete)
                    .filter(new AllureRestAssured())
                    .log().uri()
                    .contentType(JSON)
                    .delete("/users/2")
                    .then()
                    .spec(responseSpecDelete);
        });
    }

    @Test
    @Tag("APITest")
    void responseGetList() {
        step("Проверка названия цветов", () -> {
            given(requestSpecGetList)
                    .get("/unknown")
                    .then()
                    .spec(responseSpecGetList);
        });
    }
}