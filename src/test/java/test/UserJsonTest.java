package test;

import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserJsonTest {

    @Test
    public void verificaUsuario_primeiroNivel_JSON() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users/1")
                .then().statusCode(200)
                .body("id", is(1))
                .body("name", containsString("João da Silva"))
                .body("age", is(30))
                .body("salary", is(1234.5678F));
    }

    @Test
    public void verificaUsuario_segundoNivel_JSON() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users/2")
                .then().statusCode(200)
                .body("id", is(2))
                .body("name", is("Maria Joaquina"))
                .body("endereco.rua", is("Rua dos bobos"))
                .body("endereco.numero", is(0))
                .body("age", is(25))
                .body("salary", is(2500));
    }

    @Test
    public void verificaUsuario_lista_JSON() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users/3")
                .then().statusCode(200)
                .body("id", is(3))
                .body("name", is("Ana Júlia"))
                .body("age", is(20))
                .body("filhos", hasSize(2))
                .body("filhos[0].name", is("Zezinho"))
                .body("filhos[1].name", is("Luizinho"));
        /* ou podera ser feito
         * .body("filhos.name", hasItems("Zezinho", "Luizinho"))
         * */
    }

    @Test
    public void verificaUsuarioInexistente_JSON() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users/4")
                .then()
                .statusCode(404);// 404 codigo retornado quando o conteudo nao eh encontrado

    }

    @Test
    public void verificaUsuario_listaNaRaiz_JSON() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users")
                .then().statusCode(200)
                .body("$", hasSize(3));
    }


}
