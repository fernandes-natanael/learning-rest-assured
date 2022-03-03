package test;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RequestTest {

    @Test
    public void salvandoUsuario_JSON() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\": \"Skylab\", \"age\": 54 }")
                .when()
                .post("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Skylab"))
                .body("age", is(54));
    }

    @Test
    public void testPostSemName_JSON() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{ \"age\": 54 }")
                .when()
                .post("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .statusCode(400)
                .body("id", is(nullValue()))
                .body("error", is("Name é um atributo obrigatório"));
    }

    @Test
    public void salvandoUsuario_XML() {
        given()
                .log().all()
                .contentType(ContentType.XML)
                .body("<user><name>Jeverson Abel</name><age>30</age><salary>69.99</salary></user>")
                .when()
                .post("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(201)
                .rootPath("user")
                .body("@id", is(notNullValue()))
                .body("name", is("Jeverson Abel"))
                .body("age", is("30"))
                .body("salary", is("69.99"));

    }

    @Test
    public void alterandoUsuario_PUT_JSON() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\": \"Rogerio Skylab Apenas\", \"age\": 24 }")
                .when()
                .put("https://restapi.wcaquino.me/users/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(notNullValue()))
                .body("name", is("Rogerio Skylab Apenas"))
                .body("age", is(24));
    }

    @Test
    public void alterandoUsuario_URLparametrizada_PUT_JSON() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\": \"Rogerio Skylab Apenas\", \"age\": 24 }")
                .when()
                .pathParams("entidade", "users")
                .pathParams("userId", 1)
//                .put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
                .put("https://restapi.wcaquino.me/{entidade}/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(notNullValue()))
                .body("name", is("Rogerio Skylab Apenas"))
                .body("age", is(24));
    }

    @Test
    public void deletandoUsuario_JSON() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .delete("https://restapi.wcaquino.me/users/1")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void deletandoUsuarioInexistente_JSON() {
        given()
                .log().all()
                .contentType("application/json")
                .when()
                .delete("https://restapi.wcaquino.me/users/100")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Registro inexistente"));
    }


}
