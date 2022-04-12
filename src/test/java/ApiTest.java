import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class ApiTest {

    private static RequestSpecification spec;

    @BeforeClass
    public static void initSpec(){
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://cci-gwp-adonis-api.herokuapp.com")
                .build();
    }

    //позитивный ТК "GET запрос на получение списка юзеров"
    @Test
    public void getUsers(){

        given()
                .spec(spec)
                .basePath("/user/get/")
                .when().get()
                .then().statusCode(200)
                .body("[0].username", equalTo("stan"));

    }

    //позитивный ТК POST запрос с валидными значениями JSON для создания юзера
    @Test
    public void createUser(){

        String myJson = "{\"username\": \"TestApiTesting120422\", \"email\": \"TestApiTesting120422@gmail.com\", \"password\": \"TestApiTesting120422}";
        given()
                .spec(spec)
                .basePath("/user/create/")
                .contentType(ContentType.JSON)
                .body(myJson)
                .when().post()
                .then()
                .statusCode(201)
                .body("username", equalTo("TestApiTesting120422"));

    }

    //позитивный ТК POST запрос с невалидными значениями JSON: с неуникальным значением username
    @Test
    public void createUserNonUniqueUsername(){

        String myJson = "{\"username\": \"stan\", \"email\": \"TestApiTesting12042022@gmail.com\", \"password\": \"TestApiTesting12042022}";
        given()
                .spec(spec)
                .basePath("/user/create/")
                .contentType(ContentType.JSON)
                .body(myJson)
                .when().post()
                .then()
                .statusCode(400)
                .body("message", equalTo("This username is taken. Try another."));

    }
}
