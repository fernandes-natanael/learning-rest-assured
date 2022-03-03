package test;


import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HelloWorldTest {
    @Test
    public void validarLabelOlaMundo() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/ola")

                .then()
                .body(is("Ola Mundo!"))
                .body(not(nullValue()));
    }

    @Test
    public void verificaCodigoRetorno() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/ola")
                .then()
                .statusCode(200);
    }
}
