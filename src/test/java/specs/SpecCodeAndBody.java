package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public class SpecCodeAndBody {
 // public static RequestCodeAndBody loginRequestSpec = with()
 //         .filter(new AllureRestAssured())
 //         .log().uri()
 //         .log().headers()
 //         .log().body()
 //         .contentType(JSON)
 //         .baseUri("https://reqres.in")
 //         .basePath("/api");

 // public static ResponseCodeAndBody loginResponseSpec = new ResponseCodeAndBody()
 //         .log(STATUS)
 //         .log(BODY)
 //         .expectStatusCode(200)
 //         .expectBody("token", notNullValue())
 //         .build();

}
