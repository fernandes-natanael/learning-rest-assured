package test;

import io.restassured.matcher.RestAssuredMatchers;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import static io.restassured.RestAssured.given;

public class SchemeXmlTest {
    @Test
    public void validaSchemaXML() {
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
        ;
    }

    @Test(expected = SAXParseException.class)
    public void validaSchemaInvalidoXML() {
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/invalidUsersXML")
                .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
        ;
    }
}
