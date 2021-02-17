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
                .log()
                .all()
                .extract()
                .response();
    }

    public static Response get(String endpoint){
        return given()
                .contentType(ContentType.JSON)
                .log().uri()
                .log().headers()
                .log().method()
                .log().body()
                .when()
                .get(endpoint)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public static Response put(String endpoint, String body){
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().uri()
                .log().headers()
                .log().method()
                .log().body()
                .when()
                .put(endpoint)
                .then()
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
                .log()
                .all()
                .extract()
                .response();
    }

    public static Response updateWithFormData(String endpoint, String name, String status) {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .body("name=" + name + "&status=" + status)
                .post(endpoint)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public static Response delete(String endpoint) {
        return given()
                .delete(endpoint)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }
}
