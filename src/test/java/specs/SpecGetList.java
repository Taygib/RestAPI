package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItem;

public class SpecGetList {
    public static RequestSpecification requestSpecGetList = with()
            .filter(withCustomTemplates())
            .log().uri()
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification responseSpecGetList = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("data.name", hasItem("true red"))
            .expectBody("data.name", hasItem("cerulean"))
            .expectBody("data.name", hasItem("fuchsia rose"))
            .expectBody("data.name", hasItem("aqua sky"))
            .expectBody("data.name", hasItem("tigerlily"))
            .expectBody("data.name", hasItem("blue turquoise"))
            .build();
}
