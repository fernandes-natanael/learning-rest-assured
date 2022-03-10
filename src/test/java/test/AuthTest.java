package test;

import base.Core;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AuthTest extends Core {

    @Test
    public void denyTokenAccessToAPI() {
        FilterableRequestSpecification requestSpecification = (FilterableRequestSpecification) RestAssured.requestSpecification;
        requestSpecification.removeHeader("Authorization");
        given()
                .when()
                .get(PATH_BILLS_ACCOUNT)
                .then()
                .statusCode(STATUS_UNAUTHORIZED_ACESS);
    }

}
