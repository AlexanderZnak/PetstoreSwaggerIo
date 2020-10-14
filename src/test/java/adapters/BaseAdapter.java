package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BaseAdapter {
    private static final String URL = "https://petstore.swagger.io";
    Response response;
    Gson gson;

    public BaseAdapter doGetRequest(String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(String.format("%s%s", URL, request))
                .then().statusCode(statusCode).contentType(ContentType.JSON)
                .extract().response();
        return this;
    }

    public BaseAdapter doPostRequestUploadFile(String keyParam, String valueParam, File uploadFile, String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .param(keyParam, valueParam)
                .multiPart(uploadFile)
                .when().post(String.format("%s%s", URL, request))
                .then().statusCode(statusCode).contentType(ContentType.JSON)
                .extract().response();
        return this;
    }

    public BaseAdapter doPostRequestWithBody(File file, String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .body(file)
                .when().post(String.format("%s%s", URL, request))
                .then().statusCode(statusCode).contentType(ContentType.JSON)
                .extract().response();
        return this;
    }

    public BaseAdapter doPostRequestWithBody(String body, String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .body(body)
                .when().post(String.format("%s%s", URL, request))
                .then().statusCode(statusCode).contentType(ContentType.JSON)
                .extract().response();
        return this;
    }

    public BaseAdapter doPutRequest(File file, String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .body(file)
                .when().put(String.format("%s%s", URL, request))
                .then().statusCode(statusCode).contentType(ContentType.JSON)
                .extract().response();
        return this;
    }

    public BaseAdapter validateResponseUploadedFile(String jsonPath, String valueParam, String fileName) {
        String actual = response.jsonPath().getString(jsonPath);
        boolean params = false;
        if (actual.contains(valueParam) & actual.contains(fileName)) {
            params = true;
        }
        assertTrue(params);
        return this;
    }

    public BaseAdapter validateResponseViaJsonPath(String jsonPath, String expected) {
        String getJsonPath = response.jsonPath().getString(jsonPath);
        assertEquals(getJsonPath, expected);
        return this;
    }

    public BaseAdapter validateResponseViaObjects(String pathName) {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        User actualUser = gson.fromJson(response.asString(), User.class);
        User expectedUser = null;
        try {
            expectedUser = gson.fromJson(new FileReader(new File(pathName)), User.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(actualUser, expectedUser);
        return this;
    }

}
