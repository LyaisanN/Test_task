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


    @Test
    public void getUsers(){

        given()
                .spec(spec)
                .basePath("/user/get/")
                .when().get()
                .then().statusCode(200)
                .body("[0].username", equalTo("stan"));

    }

    @Test
    public void createUser(){

        String myJson = "{\"username\": \"piupiumeowmeow1204202285885858585\", \"email\": \"piupiumeowmeow59595955@gmail.com\", \"password\": \"hrfhrhrh098716384040948437}";
        given()
                .spec(spec)
                .basePath("/user/create")
                .contentType("application/json")
                .body(myJson)
                .when().post()
                .then().statusCode(201)
                .body("username", equalTo("piupiumeowmeow12042022"));

    }
}
