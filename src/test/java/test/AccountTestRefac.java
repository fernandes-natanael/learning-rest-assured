package test;

import base.Core;
import constants.DataTransaction;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AccountTestRefac extends Core implements DataTransaction {
    @BeforeClass
    public static void login_reset() {
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
        RestAssured.get("/reset").then().statusCode(STATUS_SUCCEED);
    }

}
