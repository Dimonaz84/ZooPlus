package helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;

import static io.restassured.RestAssured.given;

public class RESTHelper {

    public static Response post(String endpoint, String body){
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().uri()
                .log().headers()
                .log().method()
                .log().body()
                .when()
                .post(endpoint)
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .response();
    }


    public static Response uploadFile(String endpoint, File file) {
        return given()
                .multiPart("file", file, "multipart/form-data")
                .post(endpoint)
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .response();
    }
}
