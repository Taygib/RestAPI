package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class SpecCodeAndBody {
    public static RequestSpecification requestSpecCodeAndBody = with()
            .filter(new AllureRestAssured())
            .log().all()
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification responseSpecCodeAndBody = new ResponseSpecBuilder()
            .log(STATUS)
            .log(ALL)
            .expectStatusCode(200)
            .expectBody("total", is(12))
            .expectBody("total_pages", is(2))
            .build();
}
