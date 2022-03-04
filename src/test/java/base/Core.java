package base;

import constants.Constants;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Core implements Constants {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = APP_URL;
        RestAssured.port = APP_PORT;
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setContentType(APP_CONTENT_TYPE);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectResponseTime(lessThan(MAX_TIMOUT));
        RestAssured.responseSpecification = responseSpecBuilder.build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeClass
    public static void login(){
        Map<String, String> login = new HashMap<>();
        login.put("email", USER_EMAIL);
        login.put("senha", USER_PASSWORD);

        String TOKEN = given()
                .body(login)
                .when()
                .post(PATH_SIGNIN)
                .then()
                .statusCode(STATUS_SUCCEED) // Request Succeed
                .body("id", is(USER_ID))
                .body("nome", is(USER_NAME))
                .extract().path("token");

        RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
    }
}
