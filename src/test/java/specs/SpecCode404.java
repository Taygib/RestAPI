package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;

public class SpecCode404 {
    public static RequestSpecification requestSpecCode404 = with()
            .filter(new AllureRestAssured())
            .log().uri()
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification responseSpecCode404 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(404)
            .build();
}
