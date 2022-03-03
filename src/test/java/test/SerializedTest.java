package test;

import models.User;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class SerializedTest {

    @Test
    public void serializarUsuarioUsandoMap_JSON(){
        Map<String, Object> params = new HashMap<>();

        params.put("name", "Renata");
        params.put("age", 25);
        params.put("salary", 25000.99F);

        given()
                .contentType("application/json")
                .body(params)
                .when()
                .post("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .body("name", is("Renata"))
                .body("age", is(25))
                .body("salary", is(25000.99F));
    }

    @Test
    public void serializarUsuarioObjeto_JSON(){
        User user = new User("Cristina", 1050.00F, 24);

        given()
                .contentType("application/json")
                .body(user)
                .when()
                .post("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("Cristina"))
                .body("salary", is(1050))
                .body("age", is(24));
    }

    @Test
    public void desserializarUsuarioObjeto_JSON(){
        User user = new User("Cristina deserializade", 1050.00F, 24);

        User userVerify = given()
                .contentType("application/json")
                .body(user)
                .when()
                .post("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class);

            assertNotEquals(notNullValue(), userVerify.getId() );
            assertEquals(user.getName(), userVerify.getName() );
            assertEquals(user.getAge(), userVerify.getAge() );
            assertEquals(user.getSalary(), userVerify.getSalary() );
    }

    @Test
    public void serializarUsuarioObjeto_XML(){
        User user = new User("Cristina serializade XML", 1050.00F, 24);

        given()
                .contentType(ContentType.XML)
                .body(user)
                .when()
                .post("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(201)
                .rootPath("user")
                .body("@id", is(notNullValue()))
                .body("name", is("Cristina serializade XML"))
                .body("age", is("24"))
                .body("salary", is("1050.0"));
    }

    @Test
    public void desserializarUsuarioObjeto_XML(){
        User user = new User("Rudrigo", 49.00F, 20);

        User userVerify = given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)
                .when()
                .post("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(201)
                .extract().as(User.class);

        assertNotEquals(notNullValue(), userVerify.getId() );
        assertEquals(user.getName(), userVerify.getName() );
        assertEquals(user.getAge(), userVerify.getAge() );
        assertEquals(user.getSalary(), userVerify.getSalary() );

    }


}
