import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.given;


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

        Response response = given()
                .spec(spec)
                .basePath("/user/get/")
                .when().get()
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("stan", response.jsonPath().getString("username[0]"));
    }

    //позитивный ТК POST запрос с валидными значениями JSON для создания юзера
    @Test
    public void createUser(){

        String myJson = "{\"username\": \"TestApiTesting120422\", \"email\": \"TestApiTesting120422@gmail.com\", \"password\": \"TestApiTesting120422}";
        Response response = given()
                .spec(spec)
                .basePath("/user/create/")
                .contentType(ContentType.JSON)
                .body(myJson)
                .when().post()
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("TestApiTesting120422", response.jsonPath().getString("username"));

    }

    //негативный ТК POST запрос с невалидными значениями JSON: с неуникальным значением username
    @Test
    public void createUserNonUniqueUsername() {

        String myJson = "{\"username\": \"stan\", \"email\": \"TestApiTesting12042022@gmail.com\", \"password\": \"TestApiTesting12042022}";
        Response response = given()
                .spec(spec)
                .basePath("/user/create/")
                .contentType(ContentType.JSON)
                .body(myJson)
                .when().post()
                .then()
                .extract().response();

        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("This username is taken. Try another.", response.jsonPath().getString("message"));


    }
}
