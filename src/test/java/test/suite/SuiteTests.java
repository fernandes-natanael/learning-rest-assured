package test.suite;

import base.Core;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.AccountBalanceTest;
import test.AccountTest;
import test.AuthTest;
import test.TransactionTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        TransactionTest.class,
        AccountBalanceTest.class,
        AuthTest.class,
})
public class SuiteTests extends Core {
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

        RestAssured.get(PATH_RESET).then().statusCode(STATUS_SUCCEED);
    }
}
