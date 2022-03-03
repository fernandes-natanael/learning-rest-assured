package test;

import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserXmlTest {

    @Test
    public void dadosUsuarioXML() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML/3")
                .then()
                .rootPath("user")
                .body("@id", is("3"))
                .body("name", is("Ana Julia"))
                .rootPath("user.filhos")
                .body("name.size()", is(2))
                .body("name[0]", is("Zezinho"))
                .body("name[1]", is("Luizinho"));
    }

    @Test
    public void consultaBancoGeral() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .body("users.user.size()", is(3))
                .body("users.user.size()", is(3))
                .body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2))
                .body("users.user.find{it.age.toInteger() == 25}.name", is("Maria Joaquina"));
    }

    @Test
    public void consultaPorXPath() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .body(hasXPath("count(/users/user)", is("3")))
                .body(hasXPath("/users/user[@id='1']"))
                .body(hasXPath("//user[@id='1']"))
                .body(hasXPath("//name[text()='Luizinho']/../../name", is("Ana Julia")))
                .body(hasXPath("//name[text()='Ana Julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
        ;
    }

}
